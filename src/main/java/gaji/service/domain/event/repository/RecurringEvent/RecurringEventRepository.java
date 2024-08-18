package gaji.service.domain.event.repository.RecurringEvent;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.domain.RecurringEvent;
import gaji.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecurringEventRepository extends JpaRepository<RecurringEvent, Long>, RecurringEventQueryDslRepository{
    List<Event> findRecurringEventsByWriter(User writer);
    Optional<RecurringEvent> findById (Long id);
}
