package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomNotice is a Querydsl query type for RoomNotice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomNotice extends EntityPathBase<RoomNotice> {

    private static final long serialVersionUID = -79654745L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomNotice roomNotice = new QRoomNotice("roomNotice");

    public final StringPath body = createString("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoom room;

    public QRoomNotice(String variable) {
        this(RoomNotice.class, forVariable(variable), INITS);
    }

    public QRoomNotice(Path<? extends RoomNotice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomNotice(PathMetadata metadata, PathInits inits) {
        this(RoomNotice.class, metadata, inits);
    }

    public QRoomNotice(Class<? extends RoomNotice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

