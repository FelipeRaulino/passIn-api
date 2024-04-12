package feliperaulino.me.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import feliperaulino.me.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {

}
