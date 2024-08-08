package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomEvent is a Querydsl query type for RoomEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomEvent extends EntityPathBase<RoomEvent> {

    private static final long serialVersionUID = 1236239083L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomEvent roomEvent = new QRoomEvent("roomEvent");

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endTime = createDate("endTime", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final QRoom room;

    public final DatePath<java.time.LocalDate> startTime = createDate("startTime", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final gaji.service.domain.user.entity.QUser user;

    public final NumberPath<Integer> weeks = createNumber("weeks", Integer.class);

    public QRoomEvent(String variable) {
        this(RoomEvent.class, forVariable(variable), INITS);
    }

    public QRoomEvent(Path<? extends RoomEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomEvent(PathMetadata metadata, PathInits inits) {
        this(RoomEvent.class, metadata, inits);
    }

    public QRoomEvent(Class<? extends RoomEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

