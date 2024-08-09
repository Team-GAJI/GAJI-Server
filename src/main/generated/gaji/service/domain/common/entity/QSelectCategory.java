package gaji.service.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelectCategory is a Querydsl query type for SelectCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelectCategory extends EntityPathBase<SelectCategory> {

    private static final long serialVersionUID = -1877073090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelectCategory selectCategory = new QSelectCategory("selectCategory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<gaji.service.domain.enums.PostTypeEnum> type = createEnum("type", gaji.service.domain.enums.PostTypeEnum.class);

    public QSelectCategory(String variable) {
        this(SelectCategory.class, forVariable(variable), INITS);
    }

    public QSelectCategory(Path<? extends SelectCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelectCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelectCategory(PathMetadata metadata, PathInits inits) {
        this(SelectCategory.class, metadata, inits);
    }

    public QSelectCategory(Class<? extends SelectCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

