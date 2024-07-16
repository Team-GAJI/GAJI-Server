package gaji.service.domain.roomPost;

import gaji.service.domain.enums.CommentStatus;
import gaji.service.domain.studyMate.StudyMate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoomComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "roomComment", cascade = CascadeType.ALL)
    private List<RoomCommentLikes> roomCommentLikesList = new ArrayList<>();

    private String commentBody;
    private Integer commentOrder;
    private Integer depth;
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
}
