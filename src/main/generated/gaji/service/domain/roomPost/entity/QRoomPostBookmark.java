package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomPostBookmark is a Querydsl query type for RoomPostBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomPostBookmark extends EntityPathBase<RoomPostBookmark> {

    private static final long serialVersionUID = 1423801797L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomPostBookmark roomPostBookmark = new QRoomPostBookmark("roomPostBookmark");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoomPost roomPost;

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomPostBookmark(String variable) {
        this(RoomPostBookmark.class, forVariable(variable), INITS);
    }

    public QRoomPostBookmark(Path<? extends RoomPostBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomPostBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomPostBookmark(PathMetadata metadata, PathInits inits) {
        this(RoomPostBookmark.class, metadata, inits);
    }

    public QRoomPostBookmark(Class<? extends RoomPostBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomPost = inits.isInitialized("roomPost") ? new QRoomPost(forProperty("roomPost"), inits.get("roomPost")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

