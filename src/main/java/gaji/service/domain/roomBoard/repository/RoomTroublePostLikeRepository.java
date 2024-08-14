package gaji.service.domain.roomBoard.repository;

import gaji.service.domain.roomBoard.entity.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTroublePostLike;
import gaji.service.domain.studyMate.entity.StudyMate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTroublePostLikeRepository extends JpaRepository<RoomTroublePostLike, Long> {
    Optional<RoomTroublePostLike> findByRoomTroublePostAndStudyMate(RoomTroublePost post, StudyMate studyMate);
}
