package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public RoomResponseDto.RoomMainDto getMainStudyRoom(Long roomId) {
        String jpql = """
            SELECT NEW gaji.service.domain.room.web.dto.RoomResponseDto.RoomMainDto(
                r.name,
                r.startDay,
                r.endDay,
                r.recruitStartDay,
                r.recruitEndDay,
                FUNCTION('DATEDIFF', r.recruitEndDay, CURRENT_DATE),
                COUNT(DISTINCT sa.id)
            )
            FROM Room r
            LEFT JOIN r.studyApplicantList sa
            WHERE r.id = :roomId
            GROUP BY r.id, r.name, r.startDay, r.endDay, r.recruitStartDay, r.recruitEndDay
        """;

        RoomResponseDto.RoomMainDto result = entityManager.createQuery(jpql, RoomResponseDto.RoomMainDto.class)
                .setParameter("roomId", roomId)
                .getSingleResult();

        return result;
    }

    public RoomResponseDto.MainRoomNoticeDto getRoomNotices(Long roomId) {
// 최신 공지사항 조회
        String latestNoticeJpql = """
        SELECT NEW gaji.service.domain.room.web.dto.RoomResponseDto$MainRoomNoticeDto$NoticePreview(
            rn.id,
            rn.title,
            CASE WHEN LENGTH(rn.body) > 30 THEN SUBSTRING(rn.body, 1, 30) || '...' ELSE rn.body END
        )
        FROM RoomNotice rn
        WHERE rn.room.id = :roomId
        ORDER BY rn.id DESC
        """;
        List<RoomResponseDto.MainRoomNoticeDto.NoticePreview> latestNotices = entityManager.createQuery(latestNoticeJpql, RoomResponseDto.MainRoomNoticeDto.NoticePreview.class)
                .setParameter("roomId", roomId)
                .setMaxResults(1)
                .getResultList();

        Long latestNoticeId = null;
        String latestNoticeBody = null;
        if (!latestNotices.isEmpty()) {
            RoomResponseDto.MainRoomNoticeDto.NoticePreview latestNotice = latestNotices.get(0);
            latestNoticeId = latestNotice.getId();
            latestNoticeBody = latestNotice.getBody();
        }

        // 최신 공지사항을 제외한 다음 4개 공지사항 제목과 내용 조회
        String noticePreviewJpql = """
        SELECT NEW gaji.service.domain.room.web.dto.RoomResponseDto$MainRoomNoticeDto$NoticePreview(
            rn.id,
            rn.title,
            CASE WHEN LENGTH(rn.body) > 30 THEN SUBSTRING(rn.body, 1, 30) || '...' ELSE rn.body END
        )
        FROM RoomNotice rn
        WHERE rn.room.id = :roomId AND rn.id < :latestNoticeId
        ORDER BY rn.id DESC
        """;
        List<RoomResponseDto.MainRoomNoticeDto.NoticePreview> noticePreviews = entityManager.createQuery(noticePreviewJpql, RoomResponseDto.MainRoomNoticeDto.NoticePreview.class)
                .setParameter("roomId", roomId)
                .setParameter("latestNoticeId", latestNoticeId)
                .setMaxResults(4)
                .getResultList();

        return RoomResponseDto.MainRoomNoticeDto.builder()
                .latestNoticeId(latestNoticeId)
                .latestNoticeBody(latestNoticeBody)
                .noticePreviews(noticePreviews)
                .build();
    }

}