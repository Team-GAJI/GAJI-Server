package gaji.service.domain.recruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyComment is a Querydsl query type for StudyComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyComment extends EntityPathBase<StudyComment> {

    private static final long serialVersionUID = -1213140203L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyComment studyComment = new QStudyComment("studyComment");

    public final gaji.service.domain.common.entity.QBaseEntity _super = new gaji.service.domain.common.entity.QBaseEntity(this);

    public final StringPath body = createString("body");

    public final NumberPath<Integer> commentOrder = createNumber("commentOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QStudyComment parent;

    public final ListPath<StudyComment, QStudyComment> replies = this.<StudyComment, QStudyComment>createList("replies", StudyComment.class, QStudyComment.class, PathInits.DIRECT2);

    public final gaji.service.domain.room.entity.QRoom room;

    public final EnumPath<gaji.service.domain.enums.CommentStatus> status = createEnum("status", gaji.service.domain.enums.CommentStatus.class);

    public final ListPath<StudyCommentLikes, QStudyCommentLikes> studyCommentLikes = this.<StudyCommentLikes, QStudyCommentLikes>createList("studyCommentLikes", StudyCommentLikes.class, QStudyCommentLikes.class, PathInits.DIRECT2);

    public final gaji.service.domain.user.entity.QUser user;

    public QStudyComment(String variable) {
        this(StudyComment.class, forVariable(variable), INITS);
    }

    public QStudyComment(Path<? extends StudyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyComment(PathMetadata metadata, PathInits inits) {
        this(StudyComment.class, metadata, inits);
    }

    public QStudyComment(Class<? extends StudyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QStudyComment(forProperty("parent"), inits.get("parent")) : null;
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

