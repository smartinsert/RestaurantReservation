package main.java.services;

import main.java.domain.Restaurant;
import main.java.domain.Slot;
import main.java.domain.Table;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {
    List<Table> showAvailableTablesFor(String restaurantName, Slot slot);
    String addRestaurant(Restaurant restaurant);
    String addTable(String restaurantName, Slot slot, Table table);
    Table getTableBy(UUID tableId);
}
