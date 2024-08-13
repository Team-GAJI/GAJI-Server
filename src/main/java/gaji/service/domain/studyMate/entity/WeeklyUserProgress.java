package gaji.service.domain.studyMate.entity;

import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class WeeklyUserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_event_id")
    private RoomEvent roomEvent;

    private Double progressPercentage;

    // 총 과제 수
    private Integer totalAssignments;

    // 완료한 과제 수
    private Integer completedAssignments;

    public static WeeklyUserProgress createEmpty() {
        return new WeeklyUserProgress();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoomEvent(RoomEvent roomEvent) {
        this.roomEvent = roomEvent;
    }

    public void setTotalAssignments(int totalAssignments) {
        this.totalAssignments = totalAssignments;
    }

    public void setCompletedAssignments(int completedAssignments) {
        this.completedAssignments = completedAssignments;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
