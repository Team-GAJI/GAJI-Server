package gaji.service.domain;

import gaji.service.domain.alram.Alarm;
import gaji.service.domain.alram.UserAlarm;
import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.Gender;
import gaji.service.domain.enums.Role;
import gaji.service.domain.enums.SocialType;
import gaji.service.domain.enums.Status;
import gaji.service.domain.message.Message;
import gaji.service.domain.psot.Comment;
import gaji.service.domain.psot.Post;
import gaji.service.domain.psot.PostBookmark;
import gaji.service.domain.recruite.*;
import gaji.service.domain.room.Event;
import gaji.service.domain.room.VoiceChatUser;
import gaji.service.domain.roomPost.*;
import gaji.service.domain.studyMate.StudyApplicant;
import gaji.service.domain.studyMate.StudyMate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SearchKeyword> searchKeywordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<StudyMate> studyMateList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomPostReport> roomPostReportList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoomPostLikes> roomPostLikesList = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoomPost> roomPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomPostBookmark> roomPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Report> ReportList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RecruitPost> recruitPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RecruitPostBookmark> recruitPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserAlarm userAlarm;

    @OneToMany(mappedBy = "user")
    private List<VoiceChatUser> voiceChatUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyApplicant> studyApplicantList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomPostFile> roomPostFileList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecruitPostLikes> recruitPostLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostBookmark> postBookmarkList = new ArrayList<>();

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime inactiveTime;
    private Role role;
    private String profileImagePth;





}
