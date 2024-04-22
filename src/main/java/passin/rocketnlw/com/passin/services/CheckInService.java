package passin.rocketnlw.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import passin.rocketnlw.com.passin.domain.attendee.Attendee;
import passin.rocketnlw.com.passin.domain.checkin.CheckInAlreadyExistException;
import passin.rocketnlw.com.passin.domain.checkin.CheckIn;
import passin.rocketnlw.com.passin.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExist(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(checkIn);
    }

    public void verifyCheckInExist(String attendeeId) {
        Optional<CheckIn> checkIn = this.getCheckIn(attendeeId);
        if (checkIn.isPresent()) throw new CheckInAlreadyExistException("Attendee is already checked!");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
