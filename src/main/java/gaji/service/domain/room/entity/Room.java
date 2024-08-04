package gaji.service.domain.room.entity;

import gaji.service.domain.curriculum.Curriculum;
import gaji.service.domain.recruite.RecruitPost;
import gaji.service.domain.roomPost.entity.RoomBoard;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.Chat;
import gaji.service.domain.studyMate.StudyApplicant;
import gaji.service.domain.studyMate.StudyMate;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="curriculum_id" )
    private Curriculum curriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "way_id")
    private Way way;


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


    private String name;
    private int headCount;
    private LocalDate startDay;
    private LocalDate endDay;


}
