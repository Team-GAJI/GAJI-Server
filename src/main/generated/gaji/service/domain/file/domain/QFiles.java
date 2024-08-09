package gaji.service.domain.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFiles is a Querydsl query type for Files
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFiles extends EntityPathBase<Files> {

    private static final long serialVersionUID = -1186603517L;

    public static final QFiles files = new QFiles("files");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originalName = createString("originalName");

    public final StringPath path = createString("path");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final StringPath type = createString("type");

    public QFiles(String variable) {
        super(Files.class, forVariable(variable));
    }

    public QFiles(Path<? extends Files> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFiles(PathMetadata metadata) {
        super(Files.class, metadata);
    }

}

