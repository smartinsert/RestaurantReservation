package main.java.dto;

public class AddRestaurantResponse extends BaseResponse {

    public AddRestaurantResponse(String id) {
        super(id);
    }

    @Override
    public String getActionName() {
        return "add restaurant";
    }
}
