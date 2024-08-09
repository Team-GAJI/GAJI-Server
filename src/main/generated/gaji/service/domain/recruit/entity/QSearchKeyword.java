package gaji.service.domain.recruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchKeyword is a Querydsl query type for SearchKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchKeyword extends EntityPathBase<SearchKeyword> {

    private static final long serialVersionUID = 1300175938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchKeyword searchKeyword = new QSearchKeyword("searchKeyword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final DateTimePath<java.time.LocalDateTime> searchDate = createDateTime("searchDate", java.time.LocalDateTime.class);

    public final gaji.service.domain.user.entity.QUser user;

    public QSearchKeyword(String variable) {
        this(SearchKeyword.class, forVariable(variable), INITS);
    }

    public QSearchKeyword(Path<? extends SearchKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchKeyword(PathMetadata metadata, PathInits inits) {
        this(SearchKeyword.class, metadata, inits);
    }

    public QSearchKeyword(Class<? extends SearchKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

