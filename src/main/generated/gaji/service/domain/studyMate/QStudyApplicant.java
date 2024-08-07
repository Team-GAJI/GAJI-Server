package gaji.service.domain.studyMate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyApplicant is a Querydsl query type for StudyApplicant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyApplicant extends EntityPathBase<StudyApplicant> {

    private static final long serialVersionUID = 763132469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyApplicant studyApplicant = new QStudyApplicant("studyApplicant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.room.entity.QRoom room;

    public final EnumPath<gaji.service.domain.enums.ApplicantStatus> status = createEnum("status", gaji.service.domain.enums.ApplicantStatus.class);

    public final gaji.service.domain.user.entity.QUser user;

    public QStudyApplicant(String variable) {
        this(StudyApplicant.class, forVariable(variable), INITS);
    }

    public QStudyApplicant(Path<? extends StudyApplicant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyApplicant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyApplicant(PathMetadata metadata, PathInits inits) {
        this(StudyApplicant.class, metadata, inits);
    }

    public QStudyApplicant(Class<? extends StudyApplicant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

