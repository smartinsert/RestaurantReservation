package main.java.services;

import main.java.domain.Reservation;
import main.java.domain.Slot;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    UUID reserveTable(UUID restaurantId, UUID userId, Slot slot, List<UUID> tables);
    boolean confirmReservation(UUID reservationId, UUID userId);
    boolean cancelReservation(UUID reservationId, UUID userId);
    List<Reservation> getAllReservationsForSlot(Slot slot);
}
