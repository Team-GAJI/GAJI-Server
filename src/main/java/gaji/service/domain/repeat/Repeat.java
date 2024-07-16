package gaji.service.domain.repeat;

import gaji.service.domain.Event;
import gaji.service.domain.enums.Frequency;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Repeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    private LocalDate StartTime;
    private LocalDate EndTime;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;


}
