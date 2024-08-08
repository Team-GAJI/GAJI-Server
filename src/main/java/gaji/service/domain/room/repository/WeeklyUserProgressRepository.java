package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.studyMate.entity.WeeklyUserProgress;
import gaji.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyUserProgressRepository extends JpaRepository<WeeklyUserProgress, Long> {
    Optional<WeeklyUserProgress> findByRoomEventAndUser(RoomEvent roomEvent, User user);
}
