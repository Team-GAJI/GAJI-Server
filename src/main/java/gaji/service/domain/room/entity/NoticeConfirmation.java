package gaji.service.domain.room.entity;

import gaji.service.domain.studyMate.entity.StudyMate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NoticeConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_notice_id")
    private RoomNotice roomNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;

    private LocalDateTime confirmedAt;

    @PrePersist
    public void prePersist() {
        this.confirmedAt = LocalDateTime.now();
    }
}