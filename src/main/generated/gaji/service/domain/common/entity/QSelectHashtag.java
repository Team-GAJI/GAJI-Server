package gaji.service.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelectHashtag is a Querydsl query type for SelectHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelectHashtag extends EntityPathBase<SelectHashtag> {

    private static final long serialVersionUID = -195916404L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelectHashtag selectHashtag = new QSelectHashtag("selectHashtag");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<gaji.service.domain.enums.PostTypeEnum> type = createEnum("type", gaji.service.domain.enums.PostTypeEnum.class);

    public QSelectHashtag(String variable) {
        this(SelectHashtag.class, forVariable(variable), INITS);
    }

    public QSelectHashtag(Path<? extends SelectHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelectHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelectHashtag(PathMetadata metadata, PathInits inits) {
        this(SelectHashtag.class, metadata, inits);
    }

    public QSelectHashtag(Class<? extends SelectHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
    }

}

