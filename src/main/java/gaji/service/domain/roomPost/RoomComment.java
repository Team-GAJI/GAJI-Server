package gaji.service.domain.roomPost;

import gaji.service.domain.studyMate.StudyMate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyMate_id")
    private StudyMate studyMate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomPost_id")
    private RoomPost roomPost;

    // 부모 댓글, 자기 참조 방식
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoomComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "roomComment", cascade = CascadeType.ALL)
    private List<RoomCommentLikes> roomCommentLikesList = new ArrayList<>();

    private String CommentBody;
    private Integer order;
    private Integer depth;
    private Boolean isDeleted;

}
