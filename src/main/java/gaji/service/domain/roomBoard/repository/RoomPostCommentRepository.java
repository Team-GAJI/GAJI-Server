package gaji.service.domain.roomBoard.repository;


import gaji.service.domain.roomBoard.entity.TroublePostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomPostCommentRepository extends JpaRepository<TroublePostComment,Long> {
}
