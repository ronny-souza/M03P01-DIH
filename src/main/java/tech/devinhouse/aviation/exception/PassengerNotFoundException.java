package tech.devinhouse.aviation.exception;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException(String message, RuntimeException e) {
        super(message, e);
    }
}
