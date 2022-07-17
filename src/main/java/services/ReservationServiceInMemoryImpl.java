package main.java.services;

import main.java.domain.Reservation;
import main.java.domain.Slot;
import main.java.domain.Table;
import main.java.exception.BadRequestException;
import main.java.providers.TableLockProvider;
import main.java.providers.TableLockProviderImpl;
import main.java.types.ReservationStatus;
import main.java.types.TableStatus;
import main.java.utils.CommonUtility;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationServiceInMemoryImpl implements ReservationService {
    private final UserService userService;
    private final SlotService slotService;
    private final RestaurantService restaurantService;
    private final TableLockProvider tableLockProvider;
    private final Map<UUID, Reservation> reservationIdToReservation;
    private final Map<UUID, UUID> userIdToReservationId;

    public ReservationServiceInMemoryImpl() {
        userService = new UserServiceInMemoryImpl();
        slotService = new SlotServiceInMemoryImpl();
        restaurantService = new RestaurantServiceInMemoryImpl();
        tableLockProvider = new TableLockProviderImpl(100);
        reservationIdToReservation = new HashMap<>();
        userIdToReservationId = new HashMap<>();
    }

    @Override
    public UUID reserveTable(UUID restaurantId, UUID userId, Slot slot, List<UUID> tables) {
        if (!slotService.doesSlotExist(slot))
            throw new BadRequestException("The slot with start time " + slot.getStartTime() + " and end time "
                    + slot.getEndTime() + " does not exist !");
        List<Reservation> allReservationsForSlot = getAllReservationsForSlot(slot);
        Set<UUID> bookedTables = allReservationsForSlot.stream()
                .flatMap(reservation -> reservation.getBookedTables().stream())
                .collect(Collectors.toUnmodifiableSet());
        for (UUID tableId: tables) {
            if (bookedTables.contains(tableId))
                throw new BadRequestException("Not enough tables in the given slot " + slot +
                        "Please retry with a different number of tables or try a different slot");
        }
        UUID reservationId = CommonUtility.generateId();
        tableLockProvider.lockTables(restaurantId, tables, userId);
        tables.forEach(tableId -> {
            Table table = restaurantService.getTableBy(tableId);
            table.updateTableStatus(TableStatus.TEMPORARILY_UNAVAILABLE);
        });
        Reservation reservation = new Reservation(userId, restaurantId, tables, slot);
        reservationIdToReservation.putIfAbsent(reservationId, reservation);
        userIdToReservationId.putIfAbsent(userId, reservationId);
        return reservationId;
    }

    @Override
    public boolean confirmReservation(UUID reservationId, UUID userId) {
        Reservation reservation = updateReservation(reservationId, userId, TableStatus.RESERVED);
        reservation.confirmBooking();
        tableLockProvider.unlockTables(reservation.getRestaurantId(), reservation.getBookedTables(), userId);
        return true;
    }

    @Override
    public boolean cancelReservation(UUID reservationId, UUID userId) {
        Reservation reservation = updateReservation(reservationId, userId, TableStatus.AVAILABLE);
        reservation.cancelBooking();
        tableLockProvider.unlockTables(reservation.getRestaurantId(), reservation.getBookedTables(), userId);
        return true;
    }

    @Override
    public List<Reservation> getAllReservationsForSlot(Slot slot) {
        if (!slotService.doesSlotExist(slot))
            throw new BadRequestException("The slot with start time " + slot.getStartTime() + " and end time "
                    + slot.getEndTime() + " does not exist !");

        return reservationIdToReservation.values().stream()
                .filter(reservation -> reservation.getReservationStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.toUnmodifiableList());
    }

    private Reservation updateReservation(UUID reservationId, UUID userId, TableStatus tableStatus) {
        String userName = userService.getUserName(userId);

        if (!userIdToReservationId.containsKey(userId)
                || (userIdToReservationId.get(userId) != null && userIdToReservationId.get(userId) != reservationId)) {
            throw new BadRequestException("The user " + userName +
                    " has not made any reservations yet!");
        }

        if (!reservationIdToReservation.containsKey(reservationId)) {
            throw new BadRequestException("There is no pending reservation with id " + reservationId);
        }

        Reservation reservation = reservationIdToReservation.get(reservationId);
        UUID restaurantId = reservation.getRestaurantId();
        for (UUID tableId: reservation.getBookedTables()) {
            if (!tableLockProvider.validateLock(restaurantId, tableId, userId))
                throw new BadRequestException("The user " + userName + " does not own the reservation with id "
                        + reservationId);
        }

        for (UUID tableId: reservation.getBookedTables()) {
            Table table = restaurantService.getTableBy(tableId);
            if (table != null) {
                table.updateTableStatus(tableStatus);
            }
        }
        return reservation;
    }
}

