package feliperaulino.me.passin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import feliperaulino.me.passin.domain.checkIn.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

  Optional<CheckIn> findByAttendeeId(String attendeeId);

}
