package gaji.service.domain.event.repository.event;

import gaji.service.domain.event.domain.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventQueryDslRepository {
    public List<Event> findEventsByDateAndUserId(LocalDate date, Long userId);
}
