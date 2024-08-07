package gaji.service.domain.studyMate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyMate is a Querydsl query type for StudyMate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyMate extends EntityPathBase<StudyMate> {

    private static final long serialVersionUID = 68040690L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyMate studyMate = new QStudyMate("studyMate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<gaji.service.domain.enums.Role> role = createEnum("role", gaji.service.domain.enums.Role.class);

    public final gaji.service.domain.room.entity.QRoom room;

    public final gaji.service.domain.user.entity.QUser user;

    public QStudyMate(String variable) {
        this(StudyMate.class, forVariable(variable), INITS);
    }

    public QStudyMate(Path<? extends StudyMate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyMate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyMate(PathMetadata metadata, PathInits inits) {
        this(StudyMate.class, metadata, inits);
    }

    public QStudyMate(Class<? extends StudyMate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

