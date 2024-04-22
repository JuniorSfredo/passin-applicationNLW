package passin.rocketnlw.com.passin.domain.checkin;

public class CheckInAlreadyExistException extends RuntimeException {

    public CheckInAlreadyExistException(String msg) {
        super(msg);
    }
}
