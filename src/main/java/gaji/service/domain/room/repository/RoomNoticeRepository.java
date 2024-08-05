package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.RoomNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomNoticeRepository extends JpaRepository<RoomNotice, Long> {
}
