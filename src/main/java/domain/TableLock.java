package main.java.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class TableLock {
    private final UUID restaurantId;
    private final UUID tableId;
    private final UUID lockedBy;
    private final Integer lockTimeoutInSeconds;
    private final LocalDateTime lockTime;

    public TableLock(UUID restaurantId, UUID tableId, UUID lockedBy, Integer lockTimeoutInSeconds, LocalDateTime lockTime) {
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.lockedBy = lockedBy;
        this.lockTimeoutInSeconds = lockTimeoutInSeconds;
        this.lockTime = lockTime;
    }

    public boolean isLockExpired() {
        LocalDateTime lockedTime = lockTime.plusSeconds(lockTimeoutInSeconds);
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(lockedTime);
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public UUID getLockedBy() {
        return lockedBy;
    }

    public static void main(String[] args) {
        String a = "/a/b/c";
        String[] split = a.split("/");
        for (String name: split)
            if (!name.isEmpty())
                System.out.println(name.strip());
    }
}
