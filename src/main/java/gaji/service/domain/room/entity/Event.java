package gaji.service.domain.room.entity;

import gaji.service.domain.User;
import gaji.service.domain.myRepeat.MyRepeat;
import gaji.service.domain.myRepeat.RepeatException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private final List<RepeatException> repeatExceptionList = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private final List<MyRepeat> myRepeatList = new ArrayList<>();

    @Column(length = 30)
    private String title;

    @Column(length = 200)
    private String description;

    private Integer weeks;

    private LocalDate startTime;

    private LocalDate endTime;

    private boolean isPublic;










}
