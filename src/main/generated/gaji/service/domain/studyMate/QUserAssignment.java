package gaji.service.domain.studyMate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAssignment is a Querydsl query type for UserAssignment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAssignment extends EntityPathBase<UserAssignment> {

    private static final long serialVersionUID = -293329932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAssignment userAssignment = new QUserAssignment("userAssignment");

    public final QAssignment assignment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isComplete = createBoolean("isComplete");

    public final gaji.service.domain.user.entity.QUser user;

    public QUserAssignment(String variable) {
        this(UserAssignment.class, forVariable(variable), INITS);
    }

    public QUserAssignment(Path<? extends UserAssignment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAssignment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAssignment(PathMetadata metadata, PathInits inits) {
        this(UserAssignment.class, metadata, inits);
    }

    public QUserAssignment(Class<? extends UserAssignment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.assignment = inits.isInitialized("assignment") ? new QAssignment(forProperty("assignment"), inits.get("assignment")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

