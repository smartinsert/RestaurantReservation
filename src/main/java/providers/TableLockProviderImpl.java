package main.java.providers;

import main.java.domain.TableLock;
import main.java.exception.BadRequestException;
import main.java.exception.TableNotAvailableException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TableLockProviderImpl implements TableLockProvider {
    private static Integer lockTimeout;
    private Map<UUID, Map<UUID, TableLock>> restaurantIdToTableIdToLock;

    public TableLockProviderImpl(Integer lockTimeout) {
        restaurantIdToTableIdToLock = new HashMap<>();
        this.lockTimeout = lockTimeout;
    }

    @Override
    public synchronized void lockTables(UUID restaurantId, List<UUID> tables, UUID user) {
        assert restaurantId != null;
        assert tables != null;
        assert user != null;

        for (UUID tableId: tables) {
            if (isTableLocked(restaurantId, tableId))
                throw new TableNotAvailableException("Table with id " + tableId + " is not available at the moment");
        }

        for (UUID tableId: tables) {
            lockTable(restaurantId, tableId, user);
        }

    }

    private boolean isTableLocked(UUID restaurantId, UUID tableId) {
        return restaurantIdToTableIdToLock.containsKey(restaurantId)
                && restaurantIdToTableIdToLock.get(restaurantId).containsKey(tableId)
                && restaurantIdToTableIdToLock.get(restaurantId).get(tableId).isLockExpired();
    }

    @Override
    public void unlockTables(UUID restaurantId, List<UUID> tables, UUID user) {
        for (UUID tableId: tables) {
            if (!validateLock(restaurantId, tableId, user))
                throw new BadRequestException("Table cannot be unlocked by " + user);
        }

        for (UUID tableId: tables) {
            unlockTable(restaurantId, tableId);
        }
    }

    @Override
    public boolean validateLock(UUID restaurantId, UUID tableId, UUID user) {
        return restaurantIdToTableIdToLock.containsKey(restaurantId)
                && restaurantIdToTableIdToLock.get(restaurantId).containsKey(tableId)
                && restaurantIdToTableIdToLock.get(restaurantId).get(tableId).getLockedBy().equals(user);
    }

    @Override
    public List<UUID> getLockedTables(UUID restaurantId) {
        return null;
    }

    private void unlockTable(UUID restaurantId, UUID tableId) {
        restaurantIdToTableIdToLock.get(restaurantId).remove(tableId);
    }

    private void lockTable(UUID restaurantId, UUID tableId, UUID user) {
        TableLock tableLock = new TableLock(restaurantId, tableId, user, lockTimeout, LocalDateTime.now());
        restaurantIdToTableIdToLock.computeIfAbsent(restaurantId, v -> new HashMap<>()).putIfAbsent(tableId, tableLock);
    }
}
