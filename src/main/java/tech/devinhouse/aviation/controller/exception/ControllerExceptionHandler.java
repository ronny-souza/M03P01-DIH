package tech.devinhouse.aviation.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.devinhouse.aviation.exception.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity handlePassengerNotFoundError(PassengerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity handleSeatNotFoundError(SeatNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    public ResponseEntity handleSeatAlreadyReservedError(SeatAlreadyReservedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PassengerNotLegalAgeForEmergencySeatException.class)
    public ResponseEntity handlePassengerNotLegalAgeError(PassengerNotLegalAgeForEmergencySeatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UncheckedBagsException.class)
    public ResponseEntity hendleUncheckedBagsError(UncheckedBagsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
