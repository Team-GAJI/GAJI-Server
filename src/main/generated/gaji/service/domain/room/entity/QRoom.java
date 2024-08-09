package gaji.service.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 748666095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final NumberPath<Integer> bookmarks = createNumber("bookmarks", Integer.class);

    public final ListPath<gaji.service.domain.studyMate.Chat, gaji.service.domain.studyMate.QChat> chatList = this.<gaji.service.domain.studyMate.Chat, gaji.service.domain.studyMate.QChat>createList("chatList", gaji.service.domain.studyMate.Chat.class, gaji.service.domain.studyMate.QChat.class, PathInits.DIRECT2);

    public final gaji.service.domain.curriculum.QCurriculum curriculum;

    public final StringPath description = createString("description");

    public final NumberPath<Integer> headCount = createNumber("headCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inviteCode = createString("inviteCode");

    public final BooleanPath isPrivate = createBoolean("isPrivate");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final ListPath<Material, QMaterial> materialList = this.<Material, QMaterial>createList("materialList", Material.class, QMaterial.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final BooleanPath peopleLimited = createBoolean("peopleLimited");

    public final NumberPath<Integer> peopleMaximum = createNumber("peopleMaximum", Integer.class);

    public final DatePath<java.time.LocalDate> recruitEndDay = createDate("recruitEndDay", java.time.LocalDate.class);

    public final ListPath<gaji.service.domain.recruit.entity.RecruitPostBookmark, gaji.service.domain.recruit.entity.QRecruitPostBookmark> recruitPostBookmarkList = this.<gaji.service.domain.recruit.entity.RecruitPostBookmark, gaji.service.domain.recruit.entity.QRecruitPostBookmark>createList("recruitPostBookmarkList", gaji.service.domain.recruit.entity.RecruitPostBookmark.class, gaji.service.domain.recruit.entity.QRecruitPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.recruit.entity.RecruitPostLikes, gaji.service.domain.recruit.entity.QRecruitPostLikes> recruitPostLikesList = this.<gaji.service.domain.recruit.entity.RecruitPostLikes, gaji.service.domain.recruit.entity.QRecruitPostLikes>createList("recruitPostLikesList", gaji.service.domain.recruit.entity.RecruitPostLikes.class, gaji.service.domain.recruit.entity.QRecruitPostLikes.class, PathInits.DIRECT2);

    public final EnumPath<gaji.service.domain.enums.RecruitPostTypeEnum> recruitPostTypeEnum = createEnum("recruitPostTypeEnum", gaji.service.domain.enums.RecruitPostTypeEnum.class);

    public final DatePath<java.time.LocalDate> recruitStartDay = createDate("recruitStartDay", java.time.LocalDate.class);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomBoard, gaji.service.domain.roomPost.entity.QRoomBoard> roomBoardList = this.<gaji.service.domain.roomPost.entity.RoomBoard, gaji.service.domain.roomPost.entity.QRoomBoard>createList("roomBoardList", gaji.service.domain.roomPost.entity.RoomBoard.class, gaji.service.domain.roomPost.entity.QRoomBoard.class, PathInits.DIRECT2);

    public final ListPath<RoomEvent, QRoomEvent> roomEventList = this.<RoomEvent, QRoomEvent>createList("roomEventList", RoomEvent.class, QRoomEvent.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant> studyApplicantList = this.<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant>createList("studyApplicantList", gaji.service.domain.studyMate.StudyApplicant.class, gaji.service.domain.studyMate.QStudyApplicant.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> studyEndDay = createDate("studyEndDay", java.time.LocalDate.class);

    public final ListPath<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate> studyMateList = this.<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate>createList("studyMateList", gaji.service.domain.studyMate.StudyMate.class, gaji.service.domain.studyMate.QStudyMate.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> studyStartDay = createDate("studyStartDay", java.time.LocalDate.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final gaji.service.domain.user.entity.QUser user;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public final ListPath<VoiceChat, QVoiceChat> voiceChatList = this.<VoiceChat, QVoiceChat>createList("voiceChatList", VoiceChat.class, QVoiceChat.class, PathInits.DIRECT2);

    public final QWay way;

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.curriculum = inits.isInitialized("curriculum") ? new gaji.service.domain.curriculum.QCurriculum(forProperty("curriculum")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
        this.way = inits.isInitialized("way") ? new QWay(forProperty("way")) : null;
    }

}

