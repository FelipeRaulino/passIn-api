package feliperaulino.me.passin.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feliperaulino.me.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import feliperaulino.me.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import feliperaulino.me.passin.domain.checkIn.exceptions.CheckInExistsException;
import feliperaulino.me.passin.domain.event.exceptions.EventFullException;
import feliperaulino.me.passin.domain.event.exceptions.EventNotFoundException;
import feliperaulino.me.passin.dtos.general.ErrorMessageDTO;

@ControllerAdvice
public class ExceptionEntityHandler {

  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<?> handleEventNotFound(EventNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(EventFullException.class)
  public ResponseEntity<?> handleEventFull(EventFullException exception) {
    return ResponseEntity.badRequest().body(new ErrorMessageDTO(exception.getMessage()));
  }

  @ExceptionHandler(AttendeeNotFoundException.class)
  public ResponseEntity<?> handleAttendeeNotFound(AttendeeNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
  public ResponseEntity<?> handleAttendeeAlreadyRegistered(AttendeeAlreadyRegisteredException exception) {
    return ResponseEntity.status(409).build();
  }

  @ExceptionHandler(CheckInExistsException.class)
  public ResponseEntity<?> handleCheckInExists(CheckInExistsException exception) {
    return ResponseEntity.status(409).build();
  }

}
