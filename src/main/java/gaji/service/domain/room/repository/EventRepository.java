package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<RoomEvent,Long> {
    Optional<RoomEvent> findByRoomId(Long roomId);
}
