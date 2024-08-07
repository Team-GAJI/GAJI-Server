package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoiceChatUser is a Querydsl query type for VoiceChatUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoiceChatUser extends EntityPathBase<VoiceChatUser> {

    private static final long serialVersionUID = 540906753L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoiceChatUser voiceChatUser = new QVoiceChatUser("voiceChatUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final gaji.service.domain.user.entity.QUser user;

    public final QVoiceChat voiceChat;

    public QVoiceChatUser(String variable) {
        this(VoiceChatUser.class, forVariable(variable), INITS);
    }

    public QVoiceChatUser(Path<? extends VoiceChatUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoiceChatUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoiceChatUser(PathMetadata metadata, PathInits inits) {
        this(VoiceChatUser.class, metadata, inits);
    }

    public QVoiceChatUser(Class<? extends VoiceChatUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
        this.voiceChat = inits.isInitialized("voiceChat") ? new QVoiceChat(forProperty("voiceChat"), inits.get("voiceChat")) : null;
    }

}

