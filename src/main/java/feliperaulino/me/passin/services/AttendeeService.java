package feliperaulino.me.passin.services;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import feliperaulino.me.passin.domain.attendee.Attendee;
import feliperaulino.me.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import feliperaulino.me.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import feliperaulino.me.passin.domain.checkIn.CheckIn;
import feliperaulino.me.passin.dtos.attendee.AttendeeBadgeDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeBadgeResponseDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeListDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeListRequestDTO;
import feliperaulino.me.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendeeService {

  private final AttendeeRepository attendeeRepository;
  private final CheckInService checkInService;

  public List<Attendee> getAllAttendeesFromEvent(String eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeeListRequestDTO getEventsAttendees(String eventId) {
    List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

    List<AttendeeListDTO> attendeeListDTOs = attendees.stream().map(attendee -> {
      Optional<CheckIn> checkin = this.checkInService.getAttendeeCheckIn(attendee.getId());
      LocalDateTime checkedInAt = checkin.isPresent() ? checkin.get().getCreatedAt() : null;

      return new AttendeeListDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(),
          checkedInAt);
    }).toList();

    return new AttendeeListRequestDTO(attendeeListDTOs);
  }

  public Attendee registerAttendee(Attendee attendee) {
    return this.attendeeRepository.save(attendee);
  }

  public void verifyAttendeeSubscription(String email, String eventId) {
    Optional<Attendee> attendee = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

    if (attendee.isPresent())
      throw new AttendeeAlreadyRegisteredException("Attendee is already registered on this event");
  }

  public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    // Verificar se o participante existe na base de dados
    Attendee attendee = this.attendeeRepository.findById(attendeeId)
        .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with id: " + attendeeId));

    // Construção de URI para realização de check-in
    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId)
        .toString();

    AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri,
        attendee.getEvent().getId());

    return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);

  }

  public void checkInAttendee(String attendeeId) {
    Attendee attendee = this.getAttendee(attendeeId);

    this.checkInService.registerCheckIn(attendee);

  }

  private Attendee getAttendee(String attendeeId) {
    return this.attendeeRepository.findById(attendeeId)
        .orElseThrow(() -> new RuntimeException("Attendee not found with id: " + attendeeId));
  }

}
