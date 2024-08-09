package gaji.service.domain.studyMate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAssignment is a Querydsl query type for Assignment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAssignment extends EntityPathBase<Assignment> {

    private static final long serialVersionUID = -668582967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAssignment assignment = new QAssignment("assignment");

    public final StringPath body = createString("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.room.entity.QRoomEvent roomEvent;

    public final ListPath<UserAssignment, QUserAssignment> userAssignmentList = this.<UserAssignment, QUserAssignment>createList("userAssignmentList", UserAssignment.class, QUserAssignment.class, PathInits.DIRECT2);

    public QAssignment(String variable) {
        this(Assignment.class, forVariable(variable), INITS);
    }

    public QAssignment(Path<? extends Assignment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAssignment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAssignment(PathMetadata metadata, PathInits inits) {
        this(Assignment.class, metadata, inits);
    }

    public QAssignment(Class<? extends Assignment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomEvent = inits.isInitialized("roomEvent") ? new gaji.service.domain.room.entity.QRoomEvent(forProperty("roomEvent"), inits.get("roomEvent")) : null;
    }

}

