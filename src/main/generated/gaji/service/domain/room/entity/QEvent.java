package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = 1722005766L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final BooleanPath allday = createBoolean("allday");

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endTime = createDate("endTime", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath meeting = createBoolean("meeting");

    public final ListPath<gaji.service.domain.myRepeat.MyRepeat, gaji.service.domain.myRepeat.QMyRepeat> myRepeatList = this.<gaji.service.domain.myRepeat.MyRepeat, gaji.service.domain.myRepeat.QMyRepeat>createList("myRepeatList", gaji.service.domain.myRepeat.MyRepeat.class, gaji.service.domain.myRepeat.QMyRepeat.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.myRepeat.RepeatException, gaji.service.domain.myRepeat.QRepeatException> repeatExceptionList = this.<gaji.service.domain.myRepeat.RepeatException, gaji.service.domain.myRepeat.QRepeatException>createList("repeatExceptionList", gaji.service.domain.myRepeat.RepeatException.class, gaji.service.domain.myRepeat.QRepeatException.class, PathInits.DIRECT2);

    public final QRoom room;

    public final DatePath<java.time.LocalDate> startTime = createDate("startTime", java.time.LocalDate.class);

    public final gaji.service.domain.user.entity.QUser user;

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

