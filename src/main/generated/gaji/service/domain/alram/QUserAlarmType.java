package gaji.service.domain.alram;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAlarmType is a Querydsl query type for UserAlarmType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAlarmType extends EntityPathBase<UserAlarmType> {

    private static final long serialVersionUID = 968964105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAlarmType userAlarmType = new QUserAlarmType("userAlarmType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<gaji.service.domain.enums.UserAlarmTypeEnum> type = createEnum("type", gaji.service.domain.enums.UserAlarmTypeEnum.class);

    public final QUserAlarm userAlarm;

    public QUserAlarmType(String variable) {
        this(UserAlarmType.class, forVariable(variable), INITS);
    }

    public QUserAlarmType(Path<? extends UserAlarmType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAlarmType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAlarmType(PathMetadata metadata, PathInits inits) {
        this(UserAlarmType.class, metadata, inits);
    }

    public QUserAlarmType(Class<? extends UserAlarmType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userAlarm = inits.isInitialized("userAlarm") ? new QUserAlarm(forProperty("userAlarm"), inits.get("userAlarm")) : null;
    }

}

