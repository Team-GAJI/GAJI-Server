package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomPost is a Querydsl query type for RoomPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomPost extends EntityPathBase<RoomPost> {

    private static final long serialVersionUID = 1372738415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomPost roomPost = new QRoomPost("roomPost");

    public final StringPath body = createString("body");

    public final NumberPath<Integer> bookmarkCount = createNumber("bookmarkCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> postTime = createDateTime("postTime", java.time.LocalDateTime.class);

    public final QRoomBoard roomBoard;

    public final ListPath<RoomComment, QRoomComment> roomCommentList = this.<RoomComment, QRoomComment>createList("roomCommentList", RoomComment.class, QRoomComment.class, PathInits.DIRECT2);

    public final ListPath<RoomPostBookmark, QRoomPostBookmark> roomPostBookmarkList = this.<RoomPostBookmark, QRoomPostBookmark>createList("roomPostBookmarkList", RoomPostBookmark.class, QRoomPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<RoomPostFile, QRoomPostFile> roomPostFileList = this.<RoomPostFile, QRoomPostFile>createList("roomPostFileList", RoomPostFile.class, QRoomPostFile.class, PathInits.DIRECT2);

    public final ListPath<RoomPostLikes, QRoomPostLikes> roomPostLikesList = this.<RoomPostLikes, QRoomPostLikes>createList("roomPostLikesList", RoomPostLikes.class, QRoomPostLikes.class, PathInits.DIRECT2);

    public final ListPath<RoomPostReport, QRoomPostReport> roomPostReportList = this.<RoomPostReport, QRoomPostReport>createList("roomPostReportList", RoomPostReport.class, QRoomPostReport.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final gaji.service.domain.user.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QRoomPost(String variable) {
        this(RoomPost.class, forVariable(variable), INITS);
    }

    public QRoomPost(Path<? extends RoomPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomPost(PathMetadata metadata, PathInits inits) {
        this(RoomPost.class, metadata, inits);
    }

    public QRoomPost(Class<? extends RoomPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomBoard = inits.isInitialized("roomBoard") ? new QRoomBoard(forProperty("roomBoard"), inits.get("roomBoard")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

