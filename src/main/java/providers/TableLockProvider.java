package main.java.providers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TableLockProvider {
    public void lockTables(UUID restaurantId, List<UUID> tables, UUID user);
    public void unlockTables(UUID restaurantId, List<UUID> tables, UUID user);
    public boolean validateLock(UUID restaurantId, UUID table, UUID user);

    public List<UUID> getLockedTables(UUID restaurantId);
}
