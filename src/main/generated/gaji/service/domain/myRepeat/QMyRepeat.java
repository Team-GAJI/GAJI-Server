package gaji.service.domain.myRepeat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyRepeat is a Querydsl query type for MyRepeat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyRepeat extends EntityPathBase<MyRepeat> {

    private static final long serialVersionUID = -1803448238L;

    public static final QMyRepeat myRepeat = new QMyRepeat("myRepeat");

    public final DatePath<java.time.LocalDate> EndTime = createDate("EndTime", java.time.LocalDate.class);

    public final EnumPath<gaji.service.domain.enums.Frequency> frequency = createEnum("frequency", gaji.service.domain.enums.Frequency.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> StartTime = createDate("StartTime", java.time.LocalDate.class);

    public QMyRepeat(String variable) {
        super(MyRepeat.class, forVariable(variable));
    }

    public QMyRepeat(Path<? extends MyRepeat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyRepeat(PathMetadata metadata) {
        super(MyRepeat.class, metadata);
    }

}

