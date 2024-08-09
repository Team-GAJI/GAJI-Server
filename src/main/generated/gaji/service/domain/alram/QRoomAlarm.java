package gaji.service.domain.alram;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomAlarm is a Querydsl query type for RoomAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomAlarm extends EntityPathBase<RoomAlarm> {

    private static final long serialVersionUID = -2143749857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomAlarm roomAlarm = new QRoomAlarm("roomAlarm");

    public final QAlarm alarm;

    public final StringPath boardName = createString("boardName");

    public final StringPath body = createString("body");

    public final NumberPath<Long> entity_id = createNumber("entity_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.room.entity.QRoom room;

    public final QRoomAlarmType type;

    public QRoomAlarm(String variable) {
        this(RoomAlarm.class, forVariable(variable), INITS);
    }

    public QRoomAlarm(Path<? extends RoomAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomAlarm(PathMetadata metadata, PathInits inits) {
        this(RoomAlarm.class, metadata, inits);
    }

    public QRoomAlarm(Class<? extends RoomAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.type = inits.isInitialized("type") ? new QRoomAlarmType(forProperty("type"), inits.get("type")) : null;
    }

}

