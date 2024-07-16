package gaji.service.domain.alram;

import gaji.service.domain.User;
import gaji.service.domain.enums.IsConfirmed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "alarm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RoomAlarm roomAlarm;

    @OneToOne(mappedBy = "alarm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserAlarm userAlarm;

    @Enumerated(EnumType.STRING)
    private IsConfirmed isConfirmed;


}
