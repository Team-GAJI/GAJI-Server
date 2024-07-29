package gaji.service.domain.studyMate;

import gaji.service.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private Integer weeks;

    @Column(length = 30)
    private String body;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<UserAssignment> userAssignmentList = new ArrayList<>();

}
