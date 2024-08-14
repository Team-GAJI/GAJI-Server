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

    @OneToMany(mappedBy = "roomTroublePost", cascade = CascadeType.ALL)
    private List<TroublePostComment> troublePostCommentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;

    @OneToMany(mappedBy = "roomTroublePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomTroublePostLike> likes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.likeCount = 0;
        this.bookmarkCount = 0;
    }

    public void addLike(RoomTroublePostLike like) {
        this.likes.add(like);
        this.likeCount++;
    }

    public void removeLike(RoomTroublePostLike like) {
        this.likes.remove(like);
        this.likeCount = Math.max(0, this.likeCount - 1);
    }
}