package passin.rocketnlw.com.passin.domain.event.exceptions;

public class EventIsFullException extends RuntimeException {
    public EventIsFullException(String msg) {
        super(msg);
    }
}
