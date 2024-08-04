package gaji.service.domain.roomPost.repository;

import gaji.service.domain.roomPost.entity.RoomPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPostRepository extends JpaRepository<RoomPost, Long> {
}
