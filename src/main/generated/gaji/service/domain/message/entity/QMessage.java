package gaji.service.domain.message.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = 169370111L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMessageBody messageBody;

    public final gaji.service.domain.user.entity.QUser other;

    public final DateTimePath<java.time.LocalDateTime> readDate = createDateTime("readDate", java.time.LocalDateTime.class);

    public final gaji.service.domain.user.entity.QUser self;

    public final DateTimePath<java.time.LocalDateTime> sendDate = createDateTime("sendDate", java.time.LocalDateTime.class);

    public final EnumPath<gaji.service.domain.message.enums.MessageTypeEnum> type = createEnum("type", gaji.service.domain.message.enums.MessageTypeEnum.class);

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.messageBody = inits.isInitialized("messageBody") ? new QMessageBody(forProperty("messageBody")) : null;
        this.other = inits.isInitialized("other") ? new gaji.service.domain.user.entity.QUser(forProperty("other")) : null;
        this.self = inits.isInitialized("self") ? new gaji.service.domain.user.entity.QUser(forProperty("self")) : null;
    }

}

