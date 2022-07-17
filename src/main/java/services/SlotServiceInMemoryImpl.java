package main.java.services;

import main.java.domain.Slot;

import java.time.LocalTime;
import java.util.Set;

public class SlotServiceInMemoryImpl implements SlotService {

    @Override
    public Set<Slot> getAllSlots() {
        return Set.of(
                new Slot(LocalTime.parse("10:00"), LocalTime.parse("11:00")),
                new Slot(LocalTime.parse("11:00"), LocalTime.parse("12:00")),
                new Slot(LocalTime.parse("12:00"), LocalTime.parse("13:00")),
                new Slot(LocalTime.parse("13:00"), LocalTime.parse("14:00")),
                new Slot(LocalTime.parse("14:00"), LocalTime.parse("15:00")),
                new Slot(LocalTime.parse("15:00"), LocalTime.parse("16:00")));
    }

    @Override
    public boolean doesSlotExist(Slot slot) {
        return getAllSlots().contains(slot);
    }
}
