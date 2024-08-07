package gaji.service.domain.alram;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAlarm is a Querydsl query type for UserAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAlarm extends EntityPathBase<UserAlarm> {

    private static final long serialVersionUID = -736828369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAlarm userAlarm = new QUserAlarm("userAlarm");

    public final QAlarm alarm;

    public final StringPath body = createString("body");

    public final NumberPath<Long> entity_id = createNumber("entity_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserAlarmType userAlarmType;

    public QUserAlarm(String variable) {
        this(UserAlarm.class, forVariable(variable), INITS);
    }

    public QUserAlarm(Path<? extends UserAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAlarm(PathMetadata metadata, PathInits inits) {
        this(UserAlarm.class, metadata, inits);
    }

    public QUserAlarm(Class<? extends UserAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
        this.userAlarmType = inits.isInitialized("userAlarmType") ? new QUserAlarmType(forProperty("userAlarmType"), inits.get("userAlarmType")) : null;
    }

}

