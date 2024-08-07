package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomPostLikes is a Querydsl query type for RoomPostLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomPostLikes extends EntityPathBase<RoomPostLikes> {

    private static final long serialVersionUID = 1702088077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomPostLikes roomPostLikes = new QRoomPostLikes("roomPostLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoomPost roomPost;

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomPostLikes(String variable) {
        this(RoomPostLikes.class, forVariable(variable), INITS);
    }

    public QRoomPostLikes(Path<? extends RoomPostLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomPostLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomPostLikes(PathMetadata metadata, PathInits inits) {
        this(RoomPostLikes.class, metadata, inits);
    }

    public QRoomPostLikes(Class<? extends RoomPostLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomPost = inits.isInitialized("roomPost") ? new QRoomPost(forProperty("roomPost"), inits.get("roomPost")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

