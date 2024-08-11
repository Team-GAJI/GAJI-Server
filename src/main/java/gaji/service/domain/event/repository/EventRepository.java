package gaji.service.domain.event.repository;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByWriter(User writer);
}
