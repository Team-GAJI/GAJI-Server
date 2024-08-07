package gaji.service.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 246000335L;

    public static final QUser user = new QUser("user");

    public final gaji.service.domain.common.entity.QBaseEntity _super = new gaji.service.domain.common.entity.QBaseEntity(this);

    public final ListPath<gaji.service.domain.alram.Alarm, gaji.service.domain.alram.QAlarm> alarmList = this.<gaji.service.domain.alram.Alarm, gaji.service.domain.alram.QAlarm>createList("alarmList", gaji.service.domain.alram.Alarm.class, gaji.service.domain.alram.QAlarm.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> birthday = createDate("birthday", java.time.LocalDate.class);

    public final ListPath<gaji.service.domain.studyMate.ChatMessage, gaji.service.domain.studyMate.QChatMessage> chatMessageList = this.<gaji.service.domain.studyMate.ChatMessage, gaji.service.domain.studyMate.QChatMessage>createList("chatMessageList", gaji.service.domain.studyMate.ChatMessage.class, gaji.service.domain.studyMate.QChatMessage.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.ChatUser, gaji.service.domain.studyMate.QChatUser> chatUserList = this.<gaji.service.domain.studyMate.ChatUser, gaji.service.domain.studyMate.QChatUser>createList("chatUserList", gaji.service.domain.studyMate.ChatUser.class, gaji.service.domain.studyMate.QChatUser.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.post.entity.Comment, gaji.service.domain.post.entity.QComment> commentList = this.<gaji.service.domain.post.entity.Comment, gaji.service.domain.post.entity.QComment>createList("commentList", gaji.service.domain.post.entity.Comment.class, gaji.service.domain.post.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final ListPath<gaji.service.domain.room.entity.Event, gaji.service.domain.room.entity.QEvent> eventList = this.<gaji.service.domain.room.entity.Event, gaji.service.domain.room.entity.QEvent>createList("eventList", gaji.service.domain.room.entity.Event.class, gaji.service.domain.room.entity.QEvent.class, PathInits.DIRECT2);

    public final EnumPath<gaji.service.domain.enums.Gender> gender = createEnum("gender", gaji.service.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> inactiveTime = createDateTime("inactiveTime", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final ListPath<gaji.service.domain.post.entity.PostBookmark, gaji.service.domain.post.entity.QPostBookmark> postBookmarkList = this.<gaji.service.domain.post.entity.PostBookmark, gaji.service.domain.post.entity.QPostBookmark>createList("postBookmarkList", gaji.service.domain.post.entity.PostBookmark.class, gaji.service.domain.post.entity.QPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.post.entity.PostFile, gaji.service.domain.post.entity.QPostFile> postFileList = this.<gaji.service.domain.post.entity.PostFile, gaji.service.domain.post.entity.QPostFile>createList("postFileList", gaji.service.domain.post.entity.PostFile.class, gaji.service.domain.post.entity.QPostFile.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.post.entity.PostLikes, gaji.service.domain.post.entity.QPostLikes> postLikesList = this.<gaji.service.domain.post.entity.PostLikes, gaji.service.domain.post.entity.QPostLikes>createList("postLikesList", gaji.service.domain.post.entity.PostLikes.class, gaji.service.domain.post.entity.QPostLikes.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.post.entity.Post, gaji.service.domain.post.entity.QPost> postList = this.<gaji.service.domain.post.entity.Post, gaji.service.domain.post.entity.QPost>createList("postList", gaji.service.domain.post.entity.Post.class, gaji.service.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath profileImagePth = createString("profileImagePth");

    public final ListPath<gaji.service.domain.message.entity.Message, gaji.service.domain.message.entity.QMessage> receivedMessages = this.<gaji.service.domain.message.entity.Message, gaji.service.domain.message.entity.QMessage>createList("receivedMessages", gaji.service.domain.message.entity.Message.class, gaji.service.domain.message.entity.QMessage.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.recruite.RecruitPostBookmark, gaji.service.domain.recruite.QRecruitPostBookmark> recruitPostBookmarkList = this.<gaji.service.domain.recruite.RecruitPostBookmark, gaji.service.domain.recruite.QRecruitPostBookmark>createList("recruitPostBookmarkList", gaji.service.domain.recruite.RecruitPostBookmark.class, gaji.service.domain.recruite.QRecruitPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.recruite.RecruitPostLikes, gaji.service.domain.recruite.QRecruitPostLikes> recruitPostLikesList = this.<gaji.service.domain.recruite.RecruitPostLikes, gaji.service.domain.recruite.QRecruitPostLikes>createList("recruitPostLikesList", gaji.service.domain.recruite.RecruitPostLikes.class, gaji.service.domain.recruite.QRecruitPostLikes.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.recruite.RecruitPost, gaji.service.domain.recruite.QRecruitPost> recruitPostList = this.<gaji.service.domain.recruite.RecruitPost, gaji.service.domain.recruite.QRecruitPost>createList("recruitPostList", gaji.service.domain.recruite.RecruitPost.class, gaji.service.domain.recruite.QRecruitPost.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.Report, gaji.service.domain.QReport> reportList = this.<gaji.service.domain.Report, gaji.service.domain.QReport>createList("reportList", gaji.service.domain.Report.class, gaji.service.domain.QReport.class, PathInits.DIRECT2);

    public final EnumPath<gaji.service.domain.enums.ServiceRole> role = createEnum("role", gaji.service.domain.enums.ServiceRole.class);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomCommentLikes, gaji.service.domain.roomPost.entity.QRoomCommentLikes> roomCommentLikesList = this.<gaji.service.domain.roomPost.entity.RoomCommentLikes, gaji.service.domain.roomPost.entity.QRoomCommentLikes>createList("roomCommentLikesList", gaji.service.domain.roomPost.entity.RoomCommentLikes.class, gaji.service.domain.roomPost.entity.QRoomCommentLikes.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomComment, gaji.service.domain.roomPost.entity.QRoomComment> roomCommentList = this.<gaji.service.domain.roomPost.entity.RoomComment, gaji.service.domain.roomPost.entity.QRoomComment>createList("roomCommentList", gaji.service.domain.roomPost.entity.RoomComment.class, gaji.service.domain.roomPost.entity.QRoomComment.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomPostBookmark, gaji.service.domain.roomPost.entity.QRoomPostBookmark> roomPostBookmarkList = this.<gaji.service.domain.roomPost.entity.RoomPostBookmark, gaji.service.domain.roomPost.entity.QRoomPostBookmark>createList("roomPostBookmarkList", gaji.service.domain.roomPost.entity.RoomPostBookmark.class, gaji.service.domain.roomPost.entity.QRoomPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomPostFile, gaji.service.domain.roomPost.entity.QRoomPostFile> roomPostFileList = this.<gaji.service.domain.roomPost.entity.RoomPostFile, gaji.service.domain.roomPost.entity.QRoomPostFile>createList("roomPostFileList", gaji.service.domain.roomPost.entity.RoomPostFile.class, gaji.service.domain.roomPost.entity.QRoomPostFile.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomPostLikes, gaji.service.domain.roomPost.entity.QRoomPostLikes> roomPostLikesList = this.<gaji.service.domain.roomPost.entity.RoomPostLikes, gaji.service.domain.roomPost.entity.QRoomPostLikes>createList("roomPostLikesList", gaji.service.domain.roomPost.entity.RoomPostLikes.class, gaji.service.domain.roomPost.entity.QRoomPostLikes.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.roomPost.entity.RoomPost, gaji.service.domain.roomPost.entity.QRoomPost> roomPostList = this.<gaji.service.domain.roomPost.entity.RoomPost, gaji.service.domain.roomPost.entity.QRoomPost>createList("roomPostList", gaji.service.domain.roomPost.entity.RoomPost.class, gaji.service.domain.roomPost.entity.QRoomPost.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.recruite.SearchKeyword, gaji.service.domain.recruite.QSearchKeyword> searchKeywordList = this.<gaji.service.domain.recruite.SearchKeyword, gaji.service.domain.recruite.QSearchKeyword>createList("searchKeywordList", gaji.service.domain.recruite.SearchKeyword.class, gaji.service.domain.recruite.QSearchKeyword.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.message.entity.Message, gaji.service.domain.message.entity.QMessage> sentMessages = this.<gaji.service.domain.message.entity.Message, gaji.service.domain.message.entity.QMessage>createList("sentMessages", gaji.service.domain.message.entity.Message.class, gaji.service.domain.message.entity.QMessage.class, PathInits.DIRECT2);

    public final EnumPath<gaji.service.domain.enums.SocialType> socialType = createEnum("socialType", gaji.service.domain.enums.SocialType.class);

    public final EnumPath<gaji.service.domain.enums.UserActive> status = createEnum("status", gaji.service.domain.enums.UserActive.class);

    public final ListPath<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant> studyApplicantList = this.<gaji.service.domain.studyMate.StudyApplicant, gaji.service.domain.studyMate.QStudyApplicant>createList("studyApplicantList", gaji.service.domain.studyMate.StudyApplicant.class, gaji.service.domain.studyMate.QStudyApplicant.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate> studyMateList = this.<gaji.service.domain.studyMate.StudyMate, gaji.service.domain.studyMate.QStudyMate>createList("studyMateList", gaji.service.domain.studyMate.StudyMate.class, gaji.service.domain.studyMate.QStudyMate.class, PathInits.DIRECT2);

    public final ListPath<gaji.service.domain.studyMate.UserAssignment, gaji.service.domain.studyMate.QUserAssignment> userAssignmentList = this.<gaji.service.domain.studyMate.UserAssignment, gaji.service.domain.studyMate.QUserAssignment>createList("userAssignmentList", gaji.service.domain.studyMate.UserAssignment.class, gaji.service.domain.studyMate.QUserAssignment.class, PathInits.DIRECT2);

    public final StringPath usernameId = createString("usernameId");

    public final ListPath<gaji.service.domain.room.entity.VoiceChatUser, gaji.service.domain.room.entity.QVoiceChatUser> voiceChatUserList = this.<gaji.service.domain.room.entity.VoiceChatUser, gaji.service.domain.room.entity.QVoiceChatUser>createList("voiceChatUserList", gaji.service.domain.room.entity.VoiceChatUser.class, gaji.service.domain.room.entity.QVoiceChatUser.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

