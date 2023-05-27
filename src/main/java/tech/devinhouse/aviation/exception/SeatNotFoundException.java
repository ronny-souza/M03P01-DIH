package tech.devinhouse.aviation.exception;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException(String message) {
        super(message);
    }

    public SeatNotFoundException(String message, RuntimeException e) {
        super(message, e);
    }
}
