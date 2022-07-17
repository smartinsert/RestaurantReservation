package main.java.services;

import main.java.domain.Slot;

import java.util.List;
import java.util.Set;

public interface SlotService {
    Set<Slot> getAllSlots();
    boolean doesSlotExist(Slot slot);
}
