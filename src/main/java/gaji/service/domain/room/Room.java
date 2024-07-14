package gaji.service.domain.room;

import gaji.service.domain.Event;
import gaji.service.studyMate.Assignment;
import gaji.service.studyMate.Chat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    // 채팅방 일대일 매핑
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Chat chat;

    //과제 매핑
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Assignment> assignmentList = new ArrayList<>();


    private String name;
    private int headCount;
    private LocalDate startDay;
    private LocalDate endDay;


}
