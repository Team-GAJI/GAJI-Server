package gaji.service.domain;

import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.Gender;
import gaji.service.domain.enums.Status;
import gaji.service.domain.roomPost.RoomPostLikes;
import gaji.service.domain.roomPost.RoomPostReport;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    private LocalDateTime inactiveTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SearchKeyword> searchKeywordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<StudyMate> studyMateList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<RoomPostReport> roomPostReportList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoomPostLikes> roomPostLikesList = new ArrayList<>();


}
