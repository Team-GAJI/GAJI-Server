package gaji.service.domain.roomBoard.repository.RoomInfo;

import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoPostCommentRepository extends JpaRepository<InfoPostComment, Long> {
}
