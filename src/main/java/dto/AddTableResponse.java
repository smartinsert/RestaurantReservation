package main.java.dto;

public class AddTableResponse extends BaseResponse {

    public AddTableResponse(String id) {
        super(id);
    }

    @Override
    public String getActionName() {
        return "add table";
    }
}
