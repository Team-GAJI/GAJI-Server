package gaji.service.domain.studyMate.repository;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.StudyMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMateRepository extends JpaRepository<StudyMate, Long> {
    Optional<StudyMate> findByUserIdAndRoomId(Long userId, Long roomId);

    List<StudyMate> findByRoom(Room room);
}