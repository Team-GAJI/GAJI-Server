package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomCommentLikes is a Querydsl query type for RoomCommentLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomCommentLikes extends EntityPathBase<RoomCommentLikes> {

    private static final long serialVersionUID = 787809388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomCommentLikes roomCommentLikes = new QRoomCommentLikes("roomCommentLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoomComment roomComment;

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomCommentLikes(String variable) {
        this(RoomCommentLikes.class, forVariable(variable), INITS);
    }

    public QRoomCommentLikes(Path<? extends RoomCommentLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomCommentLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomCommentLikes(PathMetadata metadata, PathInits inits) {
        this(RoomCommentLikes.class, metadata, inits);
    }

    public QRoomCommentLikes(Class<? extends RoomCommentLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomComment = inits.isInitialized("roomComment") ? new QRoomComment(forProperty("roomComment"), inits.get("roomComment")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

