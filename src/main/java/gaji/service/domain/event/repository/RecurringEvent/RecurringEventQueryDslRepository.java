package gaji.service.domain.event.repository.RecurringEvent;

import gaji.service.domain.event.domain.RecurringEvent;

import java.time.LocalDate;
import java.util.List;

public interface RecurringEventQueryDslRepository {
    List<RecurringEvent> findByDayOfWeekAndDate(LocalDate dateTime, Long userId);
}
