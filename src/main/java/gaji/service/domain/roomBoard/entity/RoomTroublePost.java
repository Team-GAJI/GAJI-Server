package gaji.service.domain.roomBoard.entity;

import gaji.service.domain.studyMate.entity.StudyMate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomTroublePost {
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

    @OneToMany(mappedBy = "roomTroublePost", cascade =  CascadeType.ALL)
    private List<TroublePostComment> troublePostCommentList = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.likeCount = 0;
        this.bookmarkCount = 0;
    }
}
