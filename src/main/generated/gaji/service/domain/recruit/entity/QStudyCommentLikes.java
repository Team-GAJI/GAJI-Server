package gaji.service.domain.recruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyCommentLikes is a Querydsl query type for StudyCommentLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyCommentLikes extends EntityPathBase<StudyCommentLikes> {

    private static final long serialVersionUID = 1747256743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyCommentLikes studyCommentLikes = new QStudyCommentLikes("studyCommentLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStudyComment studyComment;

    public final gaji.service.domain.user.entity.QUser user;

    public QStudyCommentLikes(String variable) {
        this(StudyCommentLikes.class, forVariable(variable), INITS);
    }

    public QStudyCommentLikes(Path<? extends StudyCommentLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyCommentLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyCommentLikes(PathMetadata metadata, PathInits inits) {
        this(StudyCommentLikes.class, metadata, inits);
    }

    public QStudyCommentLikes(Class<? extends StudyCommentLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyComment = inits.isInitialized("studyComment") ? new QStudyComment(forProperty("studyComment"), inits.get("studyComment")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

