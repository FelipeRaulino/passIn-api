package feliperaulino.me.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import feliperaulino.me.passin.dtos.attendee.AttendeeBadgeResponseDTO;
import feliperaulino.me.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeControleer {

  private final AttendeeService attendeeService;

  @GetMapping("/{attendeeId}/badge")
  public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeEventBadge(@PathVariable String attendeeId,
      UriComponentsBuilder uri) {
    AttendeeBadgeResponseDTO attendeeBadgeResponseDTO = this.attendeeService.getAttendeeBadge(attendeeId, uri);

    return ResponseEntity.ok(attendeeBadgeResponseDTO);
  }

  @PostMapping("/{attendeeId}/check-in")
  public ResponseEntity<?> checkInAttendee(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {

    this.attendeeService.checkInAttendee(attendeeId);

    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

    return ResponseEntity.created(uri).build();
  }

}
