package gaji.service.domain.recruite;

import gaji.service.domain.User;
import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.Status;
import gaji.service.domain.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "recruitPost", cascade =  CascadeType.ALL)
    private List<RecruitPostBookmark> recruitPostBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "recruitPost", cascade = CascadeType.ALL)
    private List<SelectHashtag> selectHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "recruitPost", cascade = CascadeType.ALL)
    private List<SelectCategory> selectCategoryList = new ArrayList<>();

    @Column(length = 20)
    private String title;
    private String body;
    private Integer reviews;
    // 썸네일 경로
    private String thumbnailUrl;
    private LocalDate StartTime;
    private LocalDate EndTime;
    private Boolean isRecruited;
    private Boolean isPrivate;
    //초대코드
    @Column(length = 20)
    private String invite_code;

    //게시글 상태
    @Enumerated(EnumType.STRING)
    private Status status;




}
