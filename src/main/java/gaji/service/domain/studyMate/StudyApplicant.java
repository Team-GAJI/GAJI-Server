package gaji.service.domain.studyMate;

import gaji.service.domain.user.entity.User;
import gaji.service.domain.enums.ApplicantStatus;
import gaji.service.domain.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    //수락 대기 여부
    @Enumerated(EnumType.STRING)
    private ApplicantStatus status;
}
