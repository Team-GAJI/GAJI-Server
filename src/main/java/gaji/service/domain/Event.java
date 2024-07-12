package gaji.service.domain;

import com.example.gaji_dev.domain.enums.RepeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate endTime;

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;





}
