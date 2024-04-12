package feliperaulino.me.passin.dtos.attendee;

import java.time.LocalDateTime;

public record AttendeeListDTO(String id, String name, String email, LocalDateTime createdAt,
    LocalDateTime checkedInAt) {

}
