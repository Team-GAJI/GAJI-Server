package gaji.service.domain.user.entity;


import gaji.service.domain.Report;
import gaji.service.domain.alram.Alarm;
import gaji.service.domain.common.entity.BaseEntity;
import gaji.service.domain.enums.*;
import gaji.service.domain.message.entity.Message;
import gaji.service.domain.post.entity.*;
import gaji.service.domain.recruit.entity.RecruitPostBookmark;
import gaji.service.domain.recruit.entity.RecruitPostLikes;
import gaji.service.domain.recruit.entity.SearchKeyword;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.VoiceChatUser;
import gaji.service.domain.roomPost.entity.*;
import gaji.service.domain.studyMate.*;
import gaji.service.oauth2.dto.TransferUserDTO;
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
    private List<RoomEvent> roomEventList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<StudyMate> studyMateList;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoomPostLikes> roomPostLikesList = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<RoomPost> roomPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomPostBookmark> roomPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Room> recruitPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RecruitPostBookmark> recruitPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "self")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "other")
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Alarm> alarmList = new ArrayList<>();


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

    @OneToMany(mappedBy = "user")
    private List<PostFile> postFileList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostLikes> postLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Report> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserAssignment> userAssignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ChatUser> chatUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomCommentLikes> roomCommentLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomComment> roomCommentList = new ArrayList<>();

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private UserActive status;

    private LocalDateTime inactiveTime;

    @Enumerated(EnumType.STRING)
    private ServiceRole role;
    private String profileImagePth;

    private String usernameId;

    public static User createUser(TransferUserDTO transferUserDTO) {
        User user = new User();
        user.setUsernameId(transferUserDTO.getUsernameId());
        user.setEmail(transferUserDTO.getEmail());
        user.setName(transferUserDTO.getName());
        user.setRole(transferUserDTO.getRole());
        user.setBirthday(transferUserDTO.getBirthday());
        user.setSocialType(transferUserDTO.getSocialType());
        user.setGender(transferUserDTO.getGender());
        user.setStatus(transferUserDTO.getUserActive());
        return user;
    }

    private void setStatus(UserActive userActive) {
        this.status = userActive;
    }

    private void setGender(Gender gender) {
        this.gender = gender;

    }

    private void setSocialType(SocialType socialType) {
        this.socialType = socialType;
    }

    private void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private void setRole(ServiceRole role) {
        this.role = role;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setUsernameId(String usernameId) {

        this.usernameId = usernameId;
    }


}
