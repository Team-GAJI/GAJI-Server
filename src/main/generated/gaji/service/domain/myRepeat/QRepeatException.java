package gaji.service.domain.myRepeat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRepeatException is a Querydsl query type for RepeatException
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRepeatException extends EntityPathBase<RepeatException> {

    private static final long serialVersionUID = -1379952471L;

    public static final QRepeatException repeatException = new QRepeatException("repeatException");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> isRemoved = createDate("isRemoved", java.time.LocalDate.class);

    public QRepeatException(String variable) {
        super(RepeatException.class, forVariable(variable));
    }

    public QRepeatException(Path<? extends RepeatException> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRepeatException(PathMetadata metadata) {
        super(RepeatException.class, metadata);
    }

}

