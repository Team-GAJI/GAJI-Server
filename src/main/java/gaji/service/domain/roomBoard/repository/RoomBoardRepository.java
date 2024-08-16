package gaji.service.domain.roomBoard.repository;

import gaji.service.domain.roomBoard.entity.common.RoomBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomBoardRepository extends JpaRepository<RoomBoard, Long> {
    Optional<RoomBoard> findByRoomId(Long roomId);
}
