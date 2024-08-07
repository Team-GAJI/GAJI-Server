package gaji.service.domain.curriculum;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurriculum is a Querydsl query type for Curriculum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurriculum extends EntityPathBase<Curriculum> {

    private static final long serialVersionUID = -297542638L;

    public static final QCurriculum curriculum = new QCurriculum("curriculum");

    public final StringPath body = createString("body");

    public final ListPath<CurriculumFile, QCurriculumFile> curriculumFileList = this.<CurriculumFile, QCurriculumFile>createList("curriculumFileList", CurriculumFile.class, QCurriculumFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QCurriculum(String variable) {
        super(Curriculum.class, forVariable(variable));
    }

    public QCurriculum(Path<? extends Curriculum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurriculum(PathMetadata metadata) {
        super(Curriculum.class, metadata);
    }

}

