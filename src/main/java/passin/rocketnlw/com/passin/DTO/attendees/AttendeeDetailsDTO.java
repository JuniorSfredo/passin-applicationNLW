package passin.rocketnlw.com.passin.DTO.attendees;

import java.time.LocalDateTime;

public record AttendeeDetailsDTO(
        String id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime CheckedInAt) {}