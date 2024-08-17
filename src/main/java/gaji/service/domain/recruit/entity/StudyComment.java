package gaji.service.domain.recruit.entity;

import gaji.service.domain.common.entity.BaseEntity;
import gaji.service.domain.enums.CommentStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private StudyComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "studyComment", cascade = CascadeType.ALL)
    private List<StudyCommentLikes> studyCommentLikes = new ArrayList<>();

    private String body;
    private Integer commentOrder;
    private Integer depth;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;
}
