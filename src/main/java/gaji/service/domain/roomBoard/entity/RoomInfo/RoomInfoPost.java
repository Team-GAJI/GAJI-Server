package gaji.service.domain.roomBoard.entity.RoomInfo;

import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.studyMate.entity.StudyMate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomInfoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private int viewCount;
    private int likeCount;
    private int bookmarkCount;
    private LocalDateTime postTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private RoomBoard roomBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.likeCount = 0;
        this.bookmarkCount = 0;
    }

    public boolean isAuthor(Long userId){
        return this.studyMate.getUser().getId().equals(userId);
    }

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
