package gaji.service.domain.roomPost;

import gaji.service.domain.user.User;
import gaji.service.domain.enums.PostStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private int views;
    private int likes;
    private int bookmarks;

    //첨부파일
    private String file;

    @Enumerated(EnumType.STRING)
    private PostStatusEnum postStatusEnum;

    private int weeks;
}
