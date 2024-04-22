package passin.rocketnlw.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import passin.rocketnlw.com.passin.domain.event.Event;

public interface EventsRepository extends JpaRepository<Event, String> {
}
