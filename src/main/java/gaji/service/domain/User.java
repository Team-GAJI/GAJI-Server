package gaji.service.domain;

import gaji.service.domain.alram.Alarm;
import gaji.service.domain.alram.UserAlarm;
import gaji.service.domain.blog.Blog;
import gaji.service.domain.blog.BlogLikes;
import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.Gender;
import gaji.service.domain.enums.Status;
import gaji.service.domain.message.Message;
import gaji.service.domain.recruite.RecruitPost;
import gaji.service.domain.recruite.RecruitPostBookmark;
import gaji.service.domain.roomPost.RoomCommentLikes;
import gaji.service.domain.roomPost.RoomPostBookmark;
import gaji.service.domain.roomPost.RoomPostLikes;
import gaji.service.domain.roomPost.RoomPostReport;
import gaji.service.studyMate.StudyMate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomPostBookmark> roomPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomCommentLikes> roomCommentLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Report> ReportList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RecruitPost> recruitPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RecruitPostBookmark> recruitPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Blog> blogList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<BlogLikes> blogLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserAlarm userAlarm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    private LocalDateTime inactiveTime;

    @Enumerated(EnumType.STRING)
    private Status status;


}
