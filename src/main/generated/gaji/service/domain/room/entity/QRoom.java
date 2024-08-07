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

    public final ListPath<gaji.service.domain.studyMate.Assignment, gaji.service.domain.studyMate.QAssignment> assignmentList = this.<gaji.service.domain.studyMate.Assignment, gaji.service.domain.studyMate.QAssignment>createList("assignmentList", gaji.service.domain.studyMate.Assignment.class, gaji.service.domain.studyMate.QAssignment.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.Chat, gaji.service.domain.studyMate.QChat> chatList = this.<gaji.service.domain.studyMate.Chat, gaji.service.domain.studyMate.QChat>createList("chatList", gaji.service.domain.studyMate.Chat.class, gaji.service.domain.studyMate.QChat.class, PathInits.DIRECT2);

    public final gaji.service.domain.curriculum.QCurriculum curriculum;

    public final DatePath<java.time.LocalDate> endDay = createDate("endDay", java.time.LocalDate.class);

    public final ListPath<Event, QEvent> eventList = this.<Event, QEvent>createList("eventList", Event.class, QEvent.class, PathInits.DIRECT2);

    public final NumberPath<Integer> headCount = createNumber("headCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Material, QMaterial> materialList = this.<Material, QMaterial>createList("materialList", Material.class, QMaterial.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<gaji.service.domain.recruite.RecruitPost, gaji.service.domain.recruite.QRecruitPost> recruitPostList = this.<gaji.service.domain.recruite.RecruitPost, gaji.service.domain.recruite.QRecruitPost>createList("recruitPostList", gaji.service.domain.recruite.RecruitPost.class, gaji.service.domain.recruite.QRecruitPost.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomBoard, gaji.service.domain.roomPost.entity.QRoomBoard> roomBoardList = this.<gaji.service.domain.roomPost.entity.RoomBoard, gaji.service.domain.roomPost.entity.QRoomBoard>createList("roomBoardList", gaji.service.domain.roomPost.entity.RoomBoard.class, gaji.service.domain.roomPost.entity.QRoomBoard.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDay = createDate("startDay", java.time.LocalDate.class);

    public final ListPath<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant> studyApplicantList = this.<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant>createList("studyApplicantList", gaji.service.domain.studyMate.StudyApplicant.class, gaji.service.domain.studyMate.QStudyApplicant.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate> studyMateList = this.<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate>createList("studyMateList", gaji.service.domain.studyMate.StudyMate.class, gaji.service.domain.studyMate.QStudyMate.class, PathInits.DIRECT2);

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
        this.way = inits.isInitialized("way") ? new QWay(forProperty("way")) : null;
    }

}

