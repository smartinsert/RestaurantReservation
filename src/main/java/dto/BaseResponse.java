package main.java.dto;

public abstract class BaseResponse {

    private final String id;

    public BaseResponse(String id) {
        this.id = id;
    }

    public String buildSuccessResponse() {
        return getActionName() + " with  id: " + id + " has been successfully processed !";
    }

    public String buildErrorResponse(String message) {
        return getActionName() + " with  id: " + id + " has not been successfully processed due to " + message;
    }

    public abstract String getActionName();

    public String getId() {
        return id;
    }
}
