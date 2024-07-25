package gaji.service.domain.recruite;

import gaji.service.domain.user.User;
import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.RecruitePostTypeEnum;
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

    @OneToMany(mappedBy = "recruitPost", cascade = CascadeType.ALL)
    private List<RecruitPostLikes> recruitPostLikesList = new ArrayList<>();


    @Column(length = 20)
    private String title;
    private String content;

    //조회수
    private int views;

    //좋아요수
    private int likes;

    //북마크수
    private int bookmarks;

    // 썸네일 경로
    private String thumbnailPath;

    private RecruitePostTypeEnum recruitePostTypeEnum;

    private LocalDate StartTime;
    private LocalDate EndTime;

    //True: 모집완료
    private boolean isRecruited;


    // True:oepn // False : end
    private boolean isPrivate;

    //초대코드
    @Column(length = 20)
    private String inviteCode;

    //인원
    private int headCount;

    //인원제한
    private  int peopleMaximum;

    //인원제한 여부 Ture : 제한있음 / False : 제한없음
    private boolean peopleLimited;

}
