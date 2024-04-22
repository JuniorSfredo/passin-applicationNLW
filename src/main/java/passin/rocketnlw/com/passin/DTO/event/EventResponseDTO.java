package passin.rocketnlw.com.passin.DTO.event;

import lombok.Getter;
import passin.rocketnlw.com.passin.domain.event.Event;

@Getter
public class EventResponseDTO {

    EventDetailDTO eventDetailDTO;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.eventDetailDTO = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(),
                event.getMaximum_attendees(), numberOfAttendees);
    }
}
