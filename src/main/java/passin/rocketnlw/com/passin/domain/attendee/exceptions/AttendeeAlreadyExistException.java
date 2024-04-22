package passin.rocketnlw.com.passin.domain.attendee.exceptions;

public class AttendeeAlreadyExistException extends RuntimeException {
    public AttendeeAlreadyExistException (String msg) {
        super(msg);
    }
}
