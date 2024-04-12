package feliperaulino.me.passin.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import feliperaulino.me.passin.domain.attendee.Attendee;
import feliperaulino.me.passin.domain.event.Event;
import feliperaulino.me.passin.domain.event.exceptions.EventFullException;
import feliperaulino.me.passin.domain.event.exceptions.EventNotFoundException;
import feliperaulino.me.passin.dtos.attendee.AttendeeIdDTO;
import feliperaulino.me.passin.dtos.attendee.AttendeeRequestDTO;
import feliperaulino.me.passin.dtos.event.EventIdDTO;
import feliperaulino.me.passin.dtos.event.EventRequestDTO;
import feliperaulino.me.passin.dtos.event.EventResponseDTO;
import feliperaulino.me.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;
  private final AttendeeService attendeeService;

  public EventResponseDTO getEventDetails(String eventId) {
    Event event = this.checkEvent(eventId);

    List<Attendee> attendee = this.attendeeService.getAllAttendeesFromEvent(eventId);

    return new EventResponseDTO(event, attendee.size());
  }

  public EventIdDTO createEvent(EventRequestDTO eventRequest) {
    Event newEvent = new Event();

    newEvent.setTitle(eventRequest.title());
    newEvent.setDetails(eventRequest.details());
    newEvent.setMaximumAttendees(eventRequest.maximumAttendees());
    newEvent.setSlug(createSlug(eventRequest.slug()));

    this.eventRepository.save(newEvent);

    return new EventIdDTO(newEvent.getId());
  }

  public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
    // Verificação se o participante já se inscreveu no evento
    this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

    // Verificação da capacidade do evento
    Event event = this.checkEvent(eventId);
    List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

    if (event.getMaximumAttendees() <= attendees.size())
      throw new EventFullException("Event it's full");

    // Criação do participante
    Attendee attendee = new Attendee();
    attendee.setName(attendeeRequestDTO.name());
    attendee.setEmail(attendeeRequestDTO.email());
    attendee.setCreatedAt(LocalDateTime.now());
    attendee.setEvent(event);

    this.attendeeService.registerAttendee(attendee);

    return new AttendeeIdDTO(attendee.getId());

  }

  private Event checkEvent(String eventId) {
    return this.eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));
  }

  private String createSlug(String slug) {
    String normalized = Normalizer.normalize(slug, Form.NFD);

    return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
        .replaceAll("[^\\w\\s]", "")
        .replaceAll("\\s+", "-")
        .toLowerCase();
  }

}
