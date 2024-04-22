package passin.rocketnlw.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import passin.rocketnlw.com.passin.domain.checkin.CheckIn;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByAttendeeId(String AttendeeId);
}
