package gaji.service.domain.recruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitPostBookmark is a Querydsl query type for RecruitPostBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitPostBookmark extends EntityPathBase<RecruitPostBookmark> {

    private static final long serialVersionUID = 786549941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitPostBookmark recruitPostBookmark = new QRecruitPostBookmark("recruitPostBookmark");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.room.entity.QRoom room;

    public final gaji.service.domain.user.entity.QUser user;

    public QRecruitPostBookmark(String variable) {
        this(RecruitPostBookmark.class, forVariable(variable), INITS);
    }

    public QRecruitPostBookmark(Path<? extends RecruitPostBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitPostBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitPostBookmark(PathMetadata metadata, PathInits inits) {
        this(RecruitPostBookmark.class, metadata, inits);
    }

    public QRecruitPostBookmark(Class<? extends RecruitPostBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

