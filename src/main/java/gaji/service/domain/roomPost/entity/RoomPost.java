package gaji.service.domain.roomPost.entity;

import gaji.service.domain.User;
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
public class RoomPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private RoomBoard roomBoard;

    private String title;
    private String body;

    @OneToMany(mappedBy = "roomPost",cascade = CascadeType.ALL)
    private List<RoomPostReport> roomPostReportList;

    @OneToMany(mappedBy = "roomPost",cascade = CascadeType.ALL)
    private List<RoomPostLikes> roomPostLikesList;

    @OneToMany(mappedBy = "roomPost",cascade = CascadeType.ALL)
    private List<RoomPostBookmark> roomPostBookmarkList = new ArrayList<>() ;

    @OneToMany(mappedBy = "roomPost",cascade = CascadeType.ALL)
    private List<RoomComment> roomCommentList  = new ArrayList<>() ;

    @OneToMany(mappedBy = "roomPost",cascade = CascadeType.ALL)
    private List<RoomPostFile> roomPostFileList  = new ArrayList<>() ;

    //erd 설계에는 없지만 추가
    private int viewCount;
    private int likeCount;
    private int bookmarkCount;

    private LocalDateTime postTime;

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.likeCount = 0;
        this.bookmarkCount = 0;
    }
    public void setRoomBoard(RoomBoard roomBoard) {
        this.roomBoard = roomBoard;
    }
}
