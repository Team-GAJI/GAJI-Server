package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomComment is a Querydsl query type for RoomComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomComment extends EntityPathBase<RoomComment> {

    private static final long serialVersionUID = -86766288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomComment roomComment = new QRoomComment("roomComment");

    public final StringPath body = createString("body");

    public final NumberPath<Integer> commentOrder = createNumber("commentOrder", Integer.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoomComment parent;

    public final ListPath<RoomComment, QRoomComment> replies = this.<RoomComment, QRoomComment>createList("replies", RoomComment.class, QRoomComment.class, PathInits.DIRECT2);

    public final ListPath<RoomCommentLikes, QRoomCommentLikes> roomCommentLikesList = this.<RoomCommentLikes, QRoomCommentLikes>createList("roomCommentLikesList", RoomCommentLikes.class, QRoomCommentLikes.class, PathInits.DIRECT2);

    public final QRoomPost roomPost;

    public final EnumPath<gaji.service.domain.enums.UserAlarmTypeEnum> status = createEnum("status", gaji.service.domain.enums.UserAlarmTypeEnum.class);

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomComment(String variable) {
        this(RoomComment.class, forVariable(variable), INITS);
    }

    public QRoomComment(Path<? extends RoomComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomComment(PathMetadata metadata, PathInits inits) {
        this(RoomComment.class, metadata, inits);
    }

    public QRoomComment(Class<? extends RoomComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QRoomComment(forProperty("parent"), inits.get("parent")) : null;
        this.roomPost = inits.isInitialized("roomPost") ? new QRoomPost(forProperty("roomPost"), inits.get("roomPost")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

