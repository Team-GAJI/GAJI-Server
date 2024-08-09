package gaji.service.domain.term;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTermAgree is a Querydsl query type for TermAgree
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTermAgree extends EntityPathBase<TermAgree> {

    private static final long serialVersionUID = 346005210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTermAgree termAgree = new QTermAgree("termAgree");

    public final DatePath<java.time.LocalDate> agreeDate = createDate("agreeDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAgree = createBoolean("isAgree");

    public final QTerm term;

    public final gaji.service.domain.user.entity.QUser user;

    public QTermAgree(String variable) {
        this(TermAgree.class, forVariable(variable), INITS);
    }

    public QTermAgree(Path<? extends TermAgree> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTermAgree(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTermAgree(PathMetadata metadata, PathInits inits) {
        this(TermAgree.class, metadata, inits);
    }

    public QTermAgree(Class<? extends TermAgree> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.term = inits.isInitialized("term") ? new QTerm(forProperty("term")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

