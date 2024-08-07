package gaji.service.domain.message.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageBody is a Querydsl query type for MessageBody
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageBody extends EntityPathBase<MessageBody> {

    private static final long serialVersionUID = -1557596095L;

    public static final QMessageBody messageBody = new QMessageBody("messageBody");

    public final StringPath body = createString("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Message, QMessage> messageList = this.<Message, QMessage>createList("messageList", Message.class, QMessage.class, PathInits.DIRECT2);

    public QMessageBody(String variable) {
        super(MessageBody.class, forVariable(variable));
    }

    public QMessageBody(Path<? extends MessageBody> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageBody(PathMetadata metadata) {
        super(MessageBody.class, metadata);
    }

}

