package gaji.service.domain;

import gaji.service.domain.common.BaseEntity;
import gaji.service.domain.enums.Status;
import gaji.service.domain.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
