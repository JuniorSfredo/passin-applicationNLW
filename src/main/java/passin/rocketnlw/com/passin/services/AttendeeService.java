package passin.rocketnlw.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeBadgeResponseDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeDetailsDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeesListResponseDTO;
import passin.rocketnlw.com.passin.DTO.attendees.AttendeeBadgeDTO;
import passin.rocketnlw.com.passin.domain.attendee.Attendee;
import passin.rocketnlw.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import passin.rocketnlw.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import passin.rocketnlw.com.passin.domain.checkin.CheckIn;
import passin.rocketnlw.com.passin.repositories.AttendeesRepository;
import passin.rocketnlw.com.passin.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeesRepository attendeesRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeesRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee (String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);
        List<AttendeeDetailsDTO> attendeeDetailsDTOList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetailsDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(),
                    checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsDTOList);
    }

    public void verifyAttendeeSubscription(String eventId, String email) {
        Optional<Attendee> isAttendeeRegister = this.attendeesRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegister.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already exist");
    }

    public void registerAttendee(Attendee newAttendee) {
        this.attendeesRepository.save(newAttendee);
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String id, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeesRepository.findById(id).orElseThrow( () -> new AttendeeNotFoundException("Attendee not found!"));
        var uri = uriComponentsBuilder.path("/attendees/{attendeesId}/check-in").buildAndExpand(id).toUri().toString();
        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(badgeDTO);
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeesRepository.findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found!"));
    }
}
