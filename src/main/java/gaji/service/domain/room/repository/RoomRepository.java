package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.StudyMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long roomId);
}
