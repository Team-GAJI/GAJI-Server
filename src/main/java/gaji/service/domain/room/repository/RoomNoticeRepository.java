package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.RoomNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomNoticeRepository extends JpaRepository<RoomNotice, Long> {
    @Modifying
    @Query("UPDATE RoomNotice rn SET rn.confirmCount = rn.confirmCount + 1 WHERE rn.id = :noticeId")
    void incrementConfirmCount(Long noticeId);
}
