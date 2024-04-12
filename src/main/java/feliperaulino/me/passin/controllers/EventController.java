package feliperaulino.me.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import feliperaulino.me.passin.dtos.attendee.AttendeeIdDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeListRequestDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeRequestDTO;
import feliperaulino.me.passin.dtos.event.EventIdDTO;
import feliperaulino.me.passin.dtos.event.EventRequestDTO;
import feliperaulino.me.passin.dtos.event.EventResponseDTO;
import feliperaulino.me.passin.services.AttendeeService;
import feliperaulino.me.passin.services.EventService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final AttendeeService attendeeService;

  @GetMapping("/{id}")
  public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
    EventResponseDTO event = this.eventService.getEventDetails(id);
    return ResponseEntity.ok(event);
  }

  @PostMapping
  public ResponseEntity<EventIdDTO> createEvent(
      @RequestBody EventRequestDTO body,
      UriComponentsBuilder uriComponentsBuilder) {
    EventIdDTO event = this.eventService.createEvent(body);

    var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(event.eventId()).toUri();

    return ResponseEntity.created(uri).body(event);

  }

  @GetMapping("/{id}/attendees")
  public ResponseEntity<AttendeeListRequestDTO> getEventAttendees(@PathVariable String id) {
    AttendeeListRequestDTO eventAttendees = this.attendeeService.getEventsAttendees(id);

    return ResponseEntity.ok().body(eventAttendees);
  }

  @PostMapping("/{eventId}/attendees")
  public ResponseEntity<AttendeeIdDTO> registerAttendeeOnEvent(
      @PathVariable String eventId,
      @RequestBody AttendeeRequestDTO attendeeRequestDTO,
      UriComponentsBuilder uriComponentsBuilder) {

    AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, attendeeRequestDTO);

    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId())
        .toUri();

    return ResponseEntity.created(uri).body(attendeeIdDTO);
  }

}
