package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomBoard is a Querydsl query type for RoomBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomBoard extends EntityPathBase<RoomBoard> {

    private static final long serialVersionUID = -407728649L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomBoard roomBoard = new QRoomBoard("roomBoard");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final gaji.service.domain.room.entity.QRoom room;

    public final ListPath<RoomPost, QRoomPost> roomPostList = this.<RoomPost, QRoomPost>createList("roomPostList", RoomPost.class, QRoomPost.class, PathInits.DIRECT2);

    public QRoomBoard(String variable) {
        this(RoomBoard.class, forVariable(variable), INITS);
    }

    public QRoomBoard(Path<? extends RoomBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomBoard(PathMetadata metadata, PathInits inits) {
        this(RoomBoard.class, metadata, inits);
    }

    public QRoomBoard(Class<? extends RoomBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

