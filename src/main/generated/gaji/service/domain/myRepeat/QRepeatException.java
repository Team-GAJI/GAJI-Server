package gaji.service.domain.myRepeat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRepeatException is a Querydsl query type for RepeatException
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRepeatException extends EntityPathBase<RepeatException> {

    private static final long serialVersionUID = -1379952471L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRepeatException repeatException = new QRepeatException("repeatException");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final gaji.service.domain.room.entity.QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> isRemoved = createDate("isRemoved", java.time.LocalDate.class);

    public QRepeatException(String variable) {
        this(RepeatException.class, forVariable(variable), INITS);
    }

    public QRepeatException(Path<? extends RepeatException> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRepeatException(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRepeatException(PathMetadata metadata, PathInits inits) {
        this(RepeatException.class, metadata, inits);
    }

    public QRepeatException(Class<? extends RepeatException> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new gaji.service.domain.room.entity.QEvent(forProperty("event"), inits.get("event")) : null;
    }

}

