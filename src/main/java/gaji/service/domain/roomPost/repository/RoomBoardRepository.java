package gaji.service.domain.roomPost.repository;

import gaji.service.domain.roomPost.entity.RoomBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomBoardRepository extends JpaRepository<RoomBoard, Long> {
    Optional<RoomBoard> findByRoomId(Long roomId);
}
