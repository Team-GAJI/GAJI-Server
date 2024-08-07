package gaji.service.domain.myRepeat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyRepeat is a Querydsl query type for MyRepeat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyRepeat extends EntityPathBase<MyRepeat> {

    private static final long serialVersionUID = -1803448238L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyRepeat myRepeat = new QMyRepeat("myRepeat");

    public final DatePath<java.time.LocalDate> EndTime = createDate("EndTime", java.time.LocalDate.class);

    public final gaji.service.domain.room.entity.QEvent event;

    public final EnumPath<gaji.service.domain.enums.Frequency> frequency = createEnum("frequency", gaji.service.domain.enums.Frequency.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> StartTime = createDate("StartTime", java.time.LocalDate.class);

    public QMyRepeat(String variable) {
        this(MyRepeat.class, forVariable(variable), INITS);
    }

    public QMyRepeat(Path<? extends MyRepeat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyRepeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyRepeat(PathMetadata metadata, PathInits inits) {
        this(MyRepeat.class, metadata, inits);
    }

    public QMyRepeat(Class<? extends MyRepeat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new gaji.service.domain.room.entity.QEvent(forProperty("event"), inits.get("event")) : null;
    }

}

