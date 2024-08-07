package gaji.service.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFile is a Querydsl query type for PostFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostFile extends EntityPathBase<PostFile> {

    private static final long serialVersionUID = -624446955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFile postFile = new QPostFile("postFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public final QPost post;

    public final gaji.service.domain.user.entity.QUser user;

    public QPostFile(String variable) {
        this(PostFile.class, forVariable(variable), INITS);
    }

    public QPostFile(Path<? extends PostFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFile(PathMetadata metadata, PathInits inits) {
        this(PostFile.class, metadata, inits);
    }

    public QPostFile(Class<? extends PostFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

