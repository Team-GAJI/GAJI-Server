package gaji.service.domain.event.domain;

import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.user.entity.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@DiscriminatorValue("RecurringEvent")
//todo: 상속 받지 말고, 연관관계 매핑 진행
public class RecurringEvent extends Event { // 반복 일정, Event를 상속받음

    // 반복 주기를 위한 필드
    //todo: RecurrenceType, RecurrenceInterval를 사용하는 화면이 추가되면 이에 맞춰서 다시 개발, 지금은 매주 반복만 가능
    /*
    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType; // 반복 타입 (ex. 매주, 매월)

    private int recurrenceInterval; // 반복 주기 (ex. 1주, 2주, 1달)

     */

    private LocalDateTime recurrenceEndDate; // 반복 종료 날짜 (null이면 무한 반복)

    public RecurringEvent(User writer, String content, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(writer, content, startDateTime, endDateTime, true); // 반복 일정이므로 true로 설정
    }

    public RecurringEvent updateRecurringEvent(EventInfoRequest request) {
        super.updateEvent(request);

        if(!request.isRecurringStatus()) { // 반복 일정이 아니게 바뀌면
            this.recurrenceEndDate = LocalDateTime.now(); // 반복 종료 날짜를 현재 날짜로 설정
        }
        return this;
    }

    public RecurringEvent setRecurrenceEndDate(LocalDateTime recurrenceEndDate) {
        this.recurrenceEndDate = recurrenceEndDate;
        return this;
    }

}
