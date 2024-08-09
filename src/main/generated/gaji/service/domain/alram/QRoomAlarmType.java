package gaji.service.domain.alram;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomAlarmType is a Querydsl query type for RoomAlarmType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomAlarmType extends EntityPathBase<RoomAlarmType> {

    private static final long serialVersionUID = 1525765369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomAlarmType roomAlarmType = new QRoomAlarmType("roomAlarmType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoomAlarm roomAlarm;

    public final EnumPath<gaji.service.domain.enums.RoomAlarmTypeEnum> type = createEnum("type", gaji.service.domain.enums.RoomAlarmTypeEnum.class);

    public QRoomAlarmType(String variable) {
        this(RoomAlarmType.class, forVariable(variable), INITS);
    }

    public QRoomAlarmType(Path<? extends RoomAlarmType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomAlarmType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomAlarmType(PathMetadata metadata, PathInits inits) {
        this(RoomAlarmType.class, metadata, inits);
    }

    public QRoomAlarmType(Class<? extends RoomAlarmType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomAlarm = inits.isInitialized("roomAlarm") ? new QRoomAlarm(forProperty("roomAlarm"), inits.get("roomAlarm")) : null;
    }

}

