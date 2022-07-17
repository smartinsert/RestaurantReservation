package main.java.domain;

import main.java.exception.InvalidStateException;
import main.java.types.ReservationStatus;

import java.util.List;
import java.util.UUID;

public class Reservation {
    private final UUID userId;
    private final UUID restaurantId;
    private final List<UUID> bookedTables;
    private final Slot slot;
    private ReservationStatus reservationStatus;

    public Reservation(UUID userId, UUID restaurantId, List<UUID> bookedTables, Slot slot) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.bookedTables = bookedTables;
        this.slot = slot;
        this.reservationStatus = ReservationStatus.CREATED;
    }

    public void confirmBooking() {
        if (reservationStatus != ReservationStatus.CREATED)
            throw new InvalidStateException("Booking cannot be confirmed in " + reservationStatus + " status");
        reservationStatus = ReservationStatus.CONFIRMED;
    }

    public void cancelBooking() {
        reservationStatus = ReservationStatus.CANCELLED;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public List<UUID> getBookedTables() {
        return bookedTables;
    }

    public Slot getSlot() {
        return slot;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
}
