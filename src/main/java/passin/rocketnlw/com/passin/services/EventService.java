package passin.rocketnlw.com.passin.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeIdDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeRequestDTO;
import passin.rocketnlw.com.passin.DTO.event.EventIdDTO;
import passin.rocketnlw.com.passin.DTO.event.EventRequestDTO;
import passin.rocketnlw.com.passin.DTO.event.EventResponseDTO;
import passin.rocketnlw.com.passin.domain.attendee.Attendee;
import passin.rocketnlw.com.passin.domain.event.Event;
import passin.rocketnlw.com.passin.domain.event.exceptions.EventIsFullException;
import passin.rocketnlw.com.passin.domain.event.exceptions.EventNotFoundException;
import passin.rocketnlw.com.passin.repositories.EventsRepository;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
	
	private final EventsRepository eventRepository;
	private final AttendeeService attendeeService;
	
	public EventResponseDTO getEventDetail(String id) {
		Event event = this.getEventById(id);
		List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(id);
		return new EventResponseDTO(event, attendeeList.size());
	}

	public EventIdDTO createEvent(EventRequestDTO eventDTO){
		Event event = new Event();
		event.setDetails(eventDTO.details());
		event.setTitle(eventDTO.title());
		event.setMaximum_attendees(eventDTO.maximum_attendees());
		event.setSlug(this.createSlug(eventDTO.title()));

		this.eventRepository.save(event);
		return new EventIdDTO(event.getId());
	}

	public AttendeeIdDTO registerAttendeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
		this.attendeeService.verifyAttendeeSubscription(eventId, attendeeRequestDTO.email());
		Event event = this.getEventById(eventId);
		List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
		if (event.getMaximum_attendees() <= attendeeList.size()) throw new EventIsFullException("Event is full!");

		Attendee attendee = new Attendee();
		attendee.setName(attendeeRequestDTO.name());
		attendee.setEmail(attendeeRequestDTO.email());
		attendee.setEvent(event);
		attendee.setCreatedAt(LocalDateTime.now());
		this.attendeeService.registerAttendee(attendee);

		return new AttendeeIdDTO(attendee.getId());
	}

	private Event getEventById(String eventId) {
		return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!" + eventId));
	}

	private String createSlug (String text) {
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
		return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
				.replaceAll("[^\\w\\s]", "")
				.replaceAll("\\s+", "-")
				.toLowerCase();
	}
}
