package passin.rocketnlw.com.passin.DTO.event;

public record EventDetailDTO(
        String id,
        String title,
        String details,
        String slug,
        Integer maximum_attendees,
        Integer attendeesAmount) {}
