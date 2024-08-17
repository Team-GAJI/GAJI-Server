package gaji.service.domain.roomBoard.repository.RoomPost;

import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPostRepository extends JpaRepository<RoomPost, Long> {
}
