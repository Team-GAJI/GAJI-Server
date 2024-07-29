package gaji.service.domain.post.entity;

import gaji.service.domain.User;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostBookmark> postBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostFile> postFileList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikes> postLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    private String title;
    private String body;
    private int views;
    private int likes;
    private int bookmarks;

    @Enumerated(EnumType.STRING)
    private PostTypeEnum type;

    @Enumerated(EnumType.STRING)
    private PostStatusEnum status;

    @Builder
    public Post(User user, String title, String body, PostTypeEnum type, PostStatusEnum status) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.type = type;
        this.status = status;
    }

    // 엔티티 생성 시 초기값 설정
    @PrePersist
    public void prePersist() {
        this.views = 0;
        this.likes = 0;
        this.bookmarks = 0;
    }
}
