package gaji.service.domain.curriculum;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurriculumFile is a Querydsl query type for CurriculumFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurriculumFile extends EntityPathBase<CurriculumFile> {

    private static final long serialVersionUID = 840232110L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurriculumFile curriculumFile = new QCurriculumFile("curriculumFile");

    public final QCurriculum curriculum;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public QCurriculumFile(String variable) {
        this(CurriculumFile.class, forVariable(variable), INITS);
    }

    public QCurriculumFile(Path<? extends CurriculumFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurriculumFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurriculumFile(PathMetadata metadata, PathInits inits) {
        this(CurriculumFile.class, metadata, inits);
    }

    public QCurriculumFile(Class<? extends CurriculumFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.curriculum = inits.isInitialized("curriculum") ? new QCurriculum(forProperty("curriculum")) : null;
    }

}

