package main.java.domain;

import main.java.types.CuisineType;

import java.time.Instant;
import java.util.List;

public class Restaurant {
    private String restaurantName;
    private List<CuisineType> cuisineTypes;
    private String address;
    private Instant openTime;
    private Instant closeTime;

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<CuisineType> getCuisineTypes() {
        return cuisineTypes;
    }

    public String getAddress() {
        return address;
    }

    public Instant getOpenTime() {
        return openTime;
    }

    public Instant getCloseTime() {
        return closeTime;
    }
}
