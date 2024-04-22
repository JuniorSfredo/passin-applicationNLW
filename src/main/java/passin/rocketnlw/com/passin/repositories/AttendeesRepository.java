package passin.rocketnlw.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import passin.rocketnlw.com.passin.domain.attendee.Attendee;

import java.util.List;
import java.util.Optional;

public interface AttendeesRepository extends JpaRepository<Attendee, String> {

   List<Attendee> findByEventId(String eventId);
   Optional<Attendee> findByEventIdAndEmail(String eventId, String email);
}
