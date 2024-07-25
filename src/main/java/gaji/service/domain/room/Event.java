package gaji.service.domain.room;

import gaji.service.domain.user.entity.User;
import gaji.service.domain.myRepeat.MyRepeat;
import gaji.service.domain.myRepeat.RepeatException;
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
    private List<RepeatException> repeatExceptionList = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<MyRepeat> myRepeatList = new ArrayList<>();

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate endTime;

    private boolean meeting;
    private boolean allday;









}
