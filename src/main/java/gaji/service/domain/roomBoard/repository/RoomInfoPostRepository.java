package gaji.service.domain.roomBoard.repository;

import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInfoPostRepository extends JpaRepository<RoomInfoPost, Long> {
}
