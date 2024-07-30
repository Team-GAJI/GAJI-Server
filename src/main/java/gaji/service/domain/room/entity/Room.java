package gaji.service.domain.room.entity;

import gaji.service.domain.curriculum.entity.Curriculum;
import gaji.service.domain.recruit.RecruitPost;
import gaji.service.domain.roomPost.RoomBoard;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.Chat;
import gaji.service.domain.studyMate.StudyApplicant;
import gaji.service.domain.studyMate.StudyMate;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="curriculum_id" )
    private Curriculum curriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "way_id")
    private Way way;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();

    //과제 매핑
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Assignment> assignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RecruitPost> recruitPostList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<VoiceChat> voiceChatList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<StudyApplicant> studyApplicantList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<StudyMate> studyMateList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomBoard> roomBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Material> materialList = new ArrayList<>();

    public void addMaterial(Material material) {
        materialList.add(material);
    }


    private String name;
    private String description;
    private String thumbnailPath;
    private int headCount;
    // True : oepn // False : end
    private boolean isPrivate;
    private LocalDate recruitStartDay;
    private LocalDate recruitEndDay;
    private LocalDate studyStartDay;
    private LocalDate studyEndDay;

    @Builder
    public Room(String name, String description, String thumbnailPath, int headCount, boolean isPrivate, LocalDate recruitStartDay, LocalDate recruitEndDay, LocalDate studyStartDay, LocalDate studyEndDay, Curriculum curriculum, Way way) {
        this.name = name;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.headCount = headCount;
        this.isPrivate = isPrivate;
        this.recruitStartDay = recruitStartDay;
        this.recruitEndDay = recruitEndDay;
        this.studyStartDay = studyStartDay;
        this.studyEndDay = studyEndDay;
        this.curriculum = curriculum;
        this.way = way;
    }
}
