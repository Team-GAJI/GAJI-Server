package gaji.service.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSelectCategory is a Querydsl query type for SelectCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelectCategory extends EntityPathBase<SelectCategory> {

    private static final long serialVersionUID = -1877073090L;

    public static final QSelectCategory selectCategory = new QSelectCategory("selectCategory");

    public final EnumPath<gaji.service.domain.enums.CategoryEnum> category = createEnum("category", gaji.service.domain.enums.CategoryEnum.class);

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<gaji.service.domain.enums.PostTypeEnum> type = createEnum("type", gaji.service.domain.enums.PostTypeEnum.class);

    public QSelectCategory(String variable) {
        super(SelectCategory.class, forVariable(variable));
    }

    public QSelectCategory(Path<? extends SelectCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSelectCategory(PathMetadata metadata) {
        super(SelectCategory.class, metadata);
    }

}

