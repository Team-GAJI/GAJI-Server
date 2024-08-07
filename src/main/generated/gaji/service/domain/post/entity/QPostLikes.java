package gaji.service.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLikes is a Querydsl query type for PostLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLikes extends EntityPathBase<PostLikes> {

    private static final long serialVersionUID = 2122521155L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLikes postLikes = new QPostLikes("postLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public final gaji.service.domain.user.entity.QUser user;

    public QPostLikes(String variable) {
        this(PostLikes.class, forVariable(variable), INITS);
    }

    public QPostLikes(Path<? extends PostLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLikes(PathMetadata metadata, PathInits inits) {
        this(PostLikes.class, metadata, inits);
    }

    public QPostLikes(Class<? extends PostLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

