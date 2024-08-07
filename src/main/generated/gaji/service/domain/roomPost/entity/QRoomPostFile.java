package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomPostFile is a Querydsl query type for RoomPostFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomPostFile extends EntityPathBase<RoomPostFile> {

    private static final long serialVersionUID = -1330745973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomPostFile roomPostFile = new QRoomPostFile("roomPostFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public final QRoomPost roomPost;

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomPostFile(String variable) {
        this(RoomPostFile.class, forVariable(variable), INITS);
    }

    public QRoomPostFile(Path<? extends RoomPostFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomPostFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomPostFile(PathMetadata metadata, PathInits inits) {
        this(RoomPostFile.class, metadata, inits);
    }

    public QRoomPostFile(Class<? extends RoomPostFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomPost = inits.isInitialized("roomPost") ? new QRoomPost(forProperty("roomPost"), inits.get("roomPost")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

