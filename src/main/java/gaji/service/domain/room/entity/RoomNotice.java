package gaji.service.domain.room.entity;

import gaji.service.domain.studyMate.StudyMate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;


    private String title;

    private String body;

    private Integer viewCount;
    private Integer confirmCount;

    private LocalDateTime createdAt;


    //생성
    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.confirmCount = 0;
        this.createdAt = LocalDateTime.now();
    }
}
