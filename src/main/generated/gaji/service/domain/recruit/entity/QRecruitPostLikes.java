package gaji.service.domain.recruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitPostLikes is a Querydsl query type for RecruitPostLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitPostLikes extends EntityPathBase<RecruitPostLikes> {

    private static final long serialVersionUID = 1991992477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitPostLikes recruitPostLikes = new QRecruitPostLikes("recruitPostLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.room.entity.QRoom room;

    public final gaji.service.domain.user.entity.QUser user;

    public QRecruitPostLikes(String variable) {
        this(RecruitPostLikes.class, forVariable(variable), INITS);
    }

    public QRecruitPostLikes(Path<? extends RecruitPostLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitPostLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitPostLikes(PathMetadata metadata, PathInits inits) {
        this(RecruitPostLikes.class, metadata, inits);
    }

    public QRecruitPostLikes(Class<? extends RecruitPostLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

