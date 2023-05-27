package tech.devinhouse.aviation.exception;

public class PassengerNotLegalAgeForEmergencySeatException extends RuntimeException {

    public PassengerNotLegalAgeForEmergencySeatException(String message) {
        super(message);
    }

    public PassengerNotLegalAgeForEmergencySeatException(String message, RuntimeException e) {
        super(message, e);
    }
}
