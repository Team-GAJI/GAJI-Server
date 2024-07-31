package gaji.service.domain.post.entity;

import gaji.service.domain.User;
import gaji.service.domain.common.entity.BaseEntity;
import gaji.service.domain.enums.CommentStatus;
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
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //자기 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    private String body;
    private int orderNum;
    private int depth;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Builder
    public Comment(User user, Post post, Comment parent, String body) {
        this.user = user;
        this.post = post;
        this.parent = parent;
        this.body = body;
        this.status = CommentStatus.PUBLIC; // 댓글은 기본상태
        this.depth = (parent == null) ? 0 : parent.depth + 1; // 부모 댓글이 있으면 depth = (부모댓글의 depth + 1)
        this.orderNum = (parent == null) ? post.getCommentOrderNum() : parent.getOrderNum(); // orderNum은 부모 댓글이 있으면 부모 댓글과 같은 값, 없으면 증가
    }

    public void updateStatus(CommentStatus status) {
        this.status = status;
    }
}
