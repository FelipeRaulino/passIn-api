package feliperaulino.me.passin.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import feliperaulino.me.passin.domain.attendee.Attendee;
import feliperaulino.me.passin.domain.checkIn.CheckIn;
import feliperaulino.me.passin.domain.checkIn.exceptions.CheckInExistsException;
import feliperaulino.me.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckInService {

  private final CheckInRepository checkInRepository;

  public void registerCheckIn(Attendee attendee) {

    this.isCheckedIn(attendee.getId());

    CheckIn checkin = new CheckIn();

    checkin.setAttendee(attendee);
    checkin.setCreatedAt(LocalDateTime.now());

    this.checkInRepository.save(checkin);

  }

  public void isCheckedIn(String attendeeId) {
    Optional<CheckIn> checkIn = this.getAttendeeCheckIn(attendeeId);

    if (checkIn.isPresent())
      throw new CheckInExistsException("Attendee is already checked in");

  }

  public Optional<CheckIn> getAttendeeCheckIn(String attendeeId) {
    return this.checkInRepository.findByAttendeeId(attendeeId);
  }

}
