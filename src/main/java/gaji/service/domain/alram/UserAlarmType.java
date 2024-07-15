package gaji.service.domain.alram;

import gaji.service.domain.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAlarmType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "roomAlarm")
    private UserAlarm userAlarm;
}
