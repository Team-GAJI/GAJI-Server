package gaji.service.domain.event.repository.event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.domain.QEvent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class EventQueryDslRepositoryImpl implements EventQueryDslRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Event> findEventsByDateAndUserId(LocalDate date, Long userId) {
        QEvent event = QEvent.event;

        // 해당 날짜의 시작 시각과 끝 시각 계산
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return queryFactory.selectFrom(event)
                .where(event.writer.id.eq(userId)) //유저가 쓴 event
                .where(event.startDateTime.between(startOfDay, endOfDay)) //date에 맞는 event
                .fetch();
    }
}
