package tech.devinhouse.aviation.exception;

public class UncheckedBagsException extends RuntimeException {

    public UncheckedBagsException(String message) {
        super(message);
    }

    public UncheckedBagsException(String message, RuntimeException e) {
        super(message, e);
    }
}
