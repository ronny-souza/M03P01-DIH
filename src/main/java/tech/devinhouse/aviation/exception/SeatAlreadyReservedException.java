package tech.devinhouse.aviation.exception;

public class SeatAlreadyReservedException extends RuntimeException {

    public SeatAlreadyReservedException(String message) {
        super(message);
    }

    public SeatAlreadyReservedException(String message, RuntimeException e) {
        super(message, e);
    }
}
