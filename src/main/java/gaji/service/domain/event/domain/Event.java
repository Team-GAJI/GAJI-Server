package gaji.service.domain.event.domain;

import gaji.service.domain.common.entity.BaseEntity;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Inheritance(strategy = InheritanceType.JOINED)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isCompleted; // 이벤트 완료 여부

    // 기본 일정인지 반복 일정인지 구분하기 위한 필드
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isRecurring;


    @Builder
    public Event(
            User writer, String content, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isRecurring
    ) {
        this.writer = writer;
        this.content = content;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isRecurring = isRecurring;
    }

    public Event updateEvent(EventInfoRequest request) {
        this.content = request.getContent();
        this.startDateTime = request.getStartTime();
        this.endDateTime = request.getEndTime();
        this.isRecurring = request.isRecurring();

        return this;
    }

    public Event setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }
}
