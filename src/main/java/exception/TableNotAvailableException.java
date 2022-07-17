package main.java.exception;

public class TableNotAvailableException extends RuntimeException {

    public TableNotAvailableException(String message) {
        super(message);
    }

}
