package gaji.service.domain.event.repository.RecurringEvent;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.event.domain.RecurringEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static gaji.service.domain.event.QRecurringEvent.recurringEvent;

@RequiredArgsConstructor
public class RecurringEventQueryDslRepositoryImpl implements RecurringEventQueryDslRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecurringEvent> findByDayOfWeekAndDate(LocalDate date, Long userId) {

        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 요일 추출

        // 해당 날짜의 시작 시각과 끝 시각 계산
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return queryFactory.selectFrom(recurringEvent)
                .where(
                        dayOfWeekMatches(dayOfWeek), // 요일이 일치하는지 확인
                        recurringEvent.startDateTime.loe(startOfDay), // 시작 날짜가 해당 날짜 이전인지 확인
                        recurringEvent.recurrenceEndDate.isNull().or(recurringEvent.recurrenceEndDate.goe(endOfDay)) // 종료 날짜가 없거나, 해당 날짜 이후인지 확인
                )
                .where(recurringEvent.writer.id.eq(userId))
                .fetch();
    }

    // 요일이 일치하는지 확인하는 메서드
    private BooleanExpression dayOfWeekMatches(DayOfWeek dayOfWeek) {
        // 요일에 따라 조건을 추가할 수 있습니다. 예를 들어 특정 요일에만 반복되는 이벤트를 필터링합니다.
        // 예를 들어, 특정 요일에 반복되는 조건이 있는 경우 여기에 추가해야 합니다.
        // 기본적으로 요일 필터는 이벤트 엔티티에 구현되어 있어야 합니다.
        return recurringEvent.startDateTime.dayOfWeek().eq(dayOfWeek.getValue());
    }
}
