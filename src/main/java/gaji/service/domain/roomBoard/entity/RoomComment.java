package gaji.service.domain.roomBoard.entity;

import gaji.service.domain.enums.UserAlarmTypeEnum;
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
public class RoomComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private RoomPost roomPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoomComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "roomComment", cascade = CascadeType.ALL)
    private List<RoomCommentLikes> roomCommentLikesList = new ArrayList<>();

    private String body;
    private Integer commentOrder;
    private Integer depth;

    @Enumerated(EnumType.STRING)
    private UserAlarmTypeEnum status;
}
