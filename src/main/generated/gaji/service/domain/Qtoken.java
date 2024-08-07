package gaji.service.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * Qtoken is a Querydsl query type for token
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qtoken extends EntityPathBase<token> {

    private static final long serialVersionUID = 1743635997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final Qtoken token = new Qtoken("token");

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<gaji.service.domain.enums.IsRevoked> isRevoked = createEnum("isRevoked", gaji.service.domain.enums.IsRevoked.class);

    public final DateTimePath<java.time.LocalDateTime> issuedAt = createDateTime("issuedAt", java.time.LocalDateTime.class);

    public final StringPath refreshToken = createString("refreshToken");

    public final gaji.service.domain.user.entity.QUser user;

    public Qtoken(String variable) {
        this(token.class, forVariable(variable), INITS);
    }

    public Qtoken(Path<? extends token> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public Qtoken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public Qtoken(PathMetadata metadata, PathInits inits) {
        this(token.class, metadata, inits);
    }

    public Qtoken(Class<? extends token> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

