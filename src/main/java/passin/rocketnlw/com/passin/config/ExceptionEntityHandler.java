package passin.rocketnlw.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import passin.rocketnlw.com.passin.DTO.general.ErrorResponseDTO;
import passin.rocketnlw.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import passin.rocketnlw.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import passin.rocketnlw.com.passin.domain.checkin.CheckInAlreadyExistException;
import passin.rocketnlw.com.passin.domain.event.exceptions.EventIsFullException;
import passin.rocketnlw.com.passin.domain.event.exceptions.EventNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity EventIsFullException(EventIsFullException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleIsAlreadyAttendeeExist(AttendeeAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(CheckInAlreadyExistException.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
