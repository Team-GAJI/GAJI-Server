package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoiceChat is a Querydsl query type for VoiceChat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoiceChat extends EntityPathBase<VoiceChat> {

    private static final long serialVersionUID = 1308045334L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoiceChat voiceChat = new QVoiceChat("voiceChat");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoom room;

    public final ListPath<VoiceChatUser, QVoiceChatUser> voiceChatUserList = this.<VoiceChatUser, QVoiceChatUser>createList("voiceChatUserList", VoiceChatUser.class, QVoiceChatUser.class, PathInits.DIRECT2);

    public QVoiceChat(String variable) {
        this(VoiceChat.class, forVariable(variable), INITS);
    }

    public QVoiceChat(Path<? extends VoiceChat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoiceChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoiceChat(PathMetadata metadata, PathInits inits) {
        this(VoiceChat.class, metadata, inits);
    }

    public QVoiceChat(Class<? extends VoiceChat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

