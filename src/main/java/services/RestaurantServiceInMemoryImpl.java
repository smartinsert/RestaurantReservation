package main.java.services;

import main.java.domain.Restaurant;
import main.java.domain.Slot;
import main.java.domain.Table;
import main.java.dto.AddRestaurantResponse;
import main.java.dto.AddTableResponse;
import main.java.types.TableStatus;
import main.java.utils.CommonUtility;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantServiceInMemoryImpl implements RestaurantService {
    private final Map<String, UUID> restaurantNameToRestaurantUUID;
    private final Map<UUID, Table> tableIdToTable;
    private final Map<UUID, Map<Slot, List<UUID>>> restaurantIdToSlotsWithTableIds;

    public RestaurantServiceInMemoryImpl() {
        this.restaurantNameToRestaurantUUID = new HashMap<>();
        this.tableIdToTable = new HashMap<>();
        this.restaurantIdToSlotsWithTableIds = new HashMap<>();
    }

    @Override
    public List<Table> showAvailableTablesFor(String restaurantName, Slot slot) {
        UUID restaurantUUID = restaurantNameToRestaurantUUID.getOrDefault(restaurantName, null);
        if (restaurantUUID == null)
            return Collections.emptyList();
        return restaurantIdToSlotsWithTableIds.getOrDefault(restaurantUUID, new HashMap<>())
                .getOrDefault(slot, new LinkedList<>())
                .stream()
                .map(uuid -> tableIdToTable.getOrDefault(uuid, null))
                .filter(Objects::nonNull)
                .filter(table -> table.getTableStatus() == TableStatus.AVAILABLE)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String addRestaurant(Restaurant restaurant) {
        UUID generatedId = CommonUtility.generateId();
        restaurantNameToRestaurantUUID.putIfAbsent(restaurant.getRestaurantName(), generatedId);
        AddRestaurantResponse addRestaurantResponse = new AddRestaurantResponse(generatedId.toString());
        return addRestaurantResponse.buildSuccessResponse();
    }

    @Override
    public String addTable(String restaurantName, Slot slot, Table table) {
        UUID restaurantUUID = restaurantNameToRestaurantUUID.getOrDefault(restaurantName, null);
        UUID generatedTableId = CommonUtility.generateId();
        AddTableResponse addTableResponse = new AddTableResponse(generatedTableId.toString());
        if (restaurantUUID == null)
            return addTableResponse.buildErrorResponse("restaurant with name " + restaurantName
                    + " does not exists !");
        tableIdToTable.putIfAbsent(generatedTableId, table);
        restaurantIdToSlotsWithTableIds.computeIfAbsent(restaurantUUID, s -> new HashMap<>())
                .computeIfAbsent(slot, t -> new ArrayList<>()).add(generatedTableId);
        return addTableResponse.buildSuccessResponse();
    }

    @Override
    public Table getTableBy(UUID tableId) {
        return tableIdToTable.getOrDefault(tableId, null);
    }

}
