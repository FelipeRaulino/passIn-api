package feliperaulino.me.passin.dtos.event;

import feliperaulino.me.passin.domain.event.Event;
import lombok.Getter;

@Getter
public class EventResponseDTO {

  EventDetailsDTO event;

  public EventResponseDTO(Event event, Integer attendeesAmount) {
    this.event = new EventDetailsDTO(
        event.getId(),
        event.getTitle(),
        event.getDetails(),
        event.getSlug(),
        event.getMaximumAttendees(),
        attendeesAmount);
  }

}
