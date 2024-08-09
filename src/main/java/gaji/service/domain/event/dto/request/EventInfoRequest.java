package gaji.service.domain.event.dto.request;

import gaji.service.domain.event.domain.RecurrenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "일정 저장 DTO")
@Getter
@RequiredArgsConstructor
public class EventInfoRequest {

    // 일정의 설명
    private String content;

    // 일정 시작 시간
    private LocalDateTime startTime;

    // 일정 종료 시간
    private LocalDateTime endTime;

    // 일정이 반복되는지 여부
    private boolean isRecurring;

    // 반복 주기 (DAILY, WEEKLY, MONTHLY, YEARLY)
    // private RecurrenceType recurrenceType;

    // 반복 간격 (ex: 1이면 매일, 7이면 매주)
    // private int recurrenceInterval;
}
