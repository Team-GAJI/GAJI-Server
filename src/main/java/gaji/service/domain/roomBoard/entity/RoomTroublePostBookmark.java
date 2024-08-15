package gaji.service.domain.roomBoard.entity;

import gaji.service.domain.enums.PostBookmarkStatus;
import gaji.service.domain.studyMate.entity.StudyMate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomTroublePostBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_mate_id")
    private StudyMate studyMate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private RoomTroublePost roomTroublePost;

    @Enumerated(EnumType.STRING)
    private PostBookmarkStatus bookmarkStatus;

    public void setStatus(PostBookmarkStatus status) {
        this.bookmarkStatus = status;
    }
}
