package gaji.service.domain.recruit.repository;

import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {

    List<StudyComment> findByRoomAndDepth(Room room, int depth);

    int countByRoom(Room room);
}
