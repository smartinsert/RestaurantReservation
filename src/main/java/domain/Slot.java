package main.java.domain;

import java.time.LocalTime;
import java.util.Objects;

public final class Slot {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Slot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot)) return false;
        Slot slot = (Slot) o;
        return Objects.equals(getStartTime(), slot.getStartTime()) && Objects.equals(getEndTime(), slot.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getEndTime());
    }
}
