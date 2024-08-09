package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.NoticeConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeConfirmationRepository extends JpaRepository<NoticeConfirmation, Long> {
    NoticeConfirmation findByRoomNoticeIdAndStudyMateId(Long roomNoticeId, Long studyMateId);
}