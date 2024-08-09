package gaji.service.domain.roomPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomPostReport is a Querydsl query type for RoomPostReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomPostReport extends EntityPathBase<RoomPostReport> {

    private static final long serialVersionUID = 1393362307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomPostReport roomPostReport = new QRoomPostReport("roomPostReport");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<gaji.service.domain.enums.ReportPostTypeEnum> reportPostTypeEnum = createEnum("reportPostTypeEnum", gaji.service.domain.enums.ReportPostTypeEnum.class);

    public final QRoomPost roomPost;

    public final gaji.service.domain.user.entity.QUser user;

    public QRoomPostReport(String variable) {
        this(RoomPostReport.class, forVariable(variable), INITS);
    }

    public QRoomPostReport(Path<? extends RoomPostReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomPostReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomPostReport(PathMetadata metadata, PathInits inits) {
        this(RoomPostReport.class, metadata, inits);
    }

    public QRoomPostReport(Class<? extends RoomPostReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomPost = inits.isInitialized("roomPost") ? new QRoomPost(forProperty("roomPost"), inits.get("roomPost")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

