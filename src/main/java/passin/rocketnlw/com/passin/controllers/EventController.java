package passin.rocketnlw.com.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeIdDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeRequestDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeesListResponseDTO;
import passin.rocketnlw.com.passin.DTO.event.EventIdDTO;
import passin.rocketnlw.com.passin.DTO.event.EventRequestDTO;
import passin.rocketnlw.com.passin.DTO.event.EventResponseDTO;
import passin.rocketnlw.com.passin.services.AttendeeService;
import passin.rocketnlw.com.passin.services.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
	
	private final EventService eventService;
	private final AttendeeService attendeeService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
		EventResponseDTO event = this.eventService.getEventDetail(id);
		return ResponseEntity.ok(event);
	}

	@PostMapping
	public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
		EventIdDTO eventIdDTO = this.eventService.createEvent(body);
		var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
		return ResponseEntity.created(uri).body(eventIdDTO);
	}

	@GetMapping("/attendee/{id}")
	@ResponseBody
	public ResponseEntity<AttendeesListResponseDTO> getEventAttendee(@PathVariable String id) {
		AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(id);
		return ResponseEntity.ok(attendeesListResponse);
	}

	@PostMapping("/{eventId}/attendees")
	public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
		AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeOnEvent(eventId, body);
		var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.id()).toUri();
		return ResponseEntity.created(uri).body(attendeeIdDTO);
	}
}
