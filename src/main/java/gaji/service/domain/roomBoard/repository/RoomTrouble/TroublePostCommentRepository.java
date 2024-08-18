package gaji.service.domain.roomBoard.repository.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TroublePostCommentRepository extends JpaRepository<TroublePostComment, Long> {
}
