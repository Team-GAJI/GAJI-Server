package gaji.service.domain.recruit.entity;

import gaji.service.domain.User;
import gaji.service.domain.common.entity.BaseEntity;
import gaji.service.domain.enums.RecruitPostTypeEnum;
import gaji.service.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private List<RecruitPostLikes> recruitPostLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "recruitPost", cascade = CascadeType.ALL)
    private List<SelectCategory> selectCategoryList = new ArrayList<>();

    public void addCategory(SelectCategory selectCategory) {
        selectCategoryList.add(selectCategory);
    }

    @Column(length = 20)
    private String title;
    private String content;

    //조회수
    private int views;

    //좋아요수
    private int likes;

    //북마크수
    private int bookmarks;

    private RecruitPostTypeEnum recruitPostTypeEnum;

    private LocalDate startTime;
    private LocalDate endTime;

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

    @Builder
    public RecruitPost(User user, Room room, String title, String content, LocalDate startTime, LocalDate endTime, boolean isPrivate, String inviteCode, int headCount, int peopleMaximum, boolean peopleLimited) {
        this.user = user;
        this.room = room;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isPrivate = isPrivate;
        this.inviteCode = inviteCode;
        this.headCount = headCount;
        this.peopleMaximum = peopleMaximum;
        this.peopleLimited = peopleLimited;
    }

    @PrePersist
    public void prePersist() {
        this.views = 0;
        this.likes = 0;
        this.bookmarks = 0;
        this.recruitPostTypeEnum = RecruitPostTypeEnum.RECRUITING;
    }
}
