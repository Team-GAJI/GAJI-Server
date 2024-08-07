package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWay is a Querydsl query type for Way
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWay extends EntityPathBase<Way> {

    private static final long serialVersionUID = -1776960421L;

    public static final QWay way = new QWay("way");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QWay(String variable) {
        super(Way.class, forVariable(variable));
    }

    public QWay(Path<? extends Way> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWay(PathMetadata metadata) {
        super(Way.class, metadata);
    }

}

