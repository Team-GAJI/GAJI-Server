package gaji.service.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1988586745L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final gaji.service.domain.common.entity.QBaseEntity _super = new gaji.service.domain.common.entity.QBaseEntity(this);

    public final StringPath body = createString("body");

    public final NumberPath<Integer> bookmarkCnt = createNumber("bookmarkCnt", Integer.class);

    public final NumberPath<Integer> commentCnt = createNumber("commentCnt", Integer.class);

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentOrderNum = createNumber("commentOrderNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final NumberPath<Integer> popularityScore = createNumber("popularityScore", Integer.class);

    public final ListPath<PostBookmark, QPostBookmark> postBookmarkList = this.<PostBookmark, QPostBookmark>createList("postBookmarkList", PostBookmark.class, QPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<PostFile, QPostFile> postFileList = this.<PostFile, QPostFile>createList("postFileList", PostFile.class, QPostFile.class, PathInits.DIRECT2);

    public final ListPath<PostLikes, QPostLikes> postLikesList = this.<PostLikes, QPostLikes>createList("postLikesList", PostLikes.class, QPostLikes.class, PathInits.DIRECT2);

    public final EnumPath<gaji.service.domain.enums.PostStatusEnum> status = createEnum("status", gaji.service.domain.enums.PostStatusEnum.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final EnumPath<gaji.service.domain.enums.PostTypeEnum> type = createEnum("type", gaji.service.domain.enums.PostTypeEnum.class);

    public final gaji.service.domain.user.entity.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

