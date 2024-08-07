package gaji.service.domain.room.repository;

import gaji.service.domain.room.web.dto.RoomResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class RoomQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size) {
        String jpql = """
            SELECT NEW gaji.service.domain.room.web.dto.RoomResponseDto$NoticeDto(
                rn.id,
                sm.user.name,
                rn.title,
                rn.body,
                rn.confirmCount,
                rn.createdAt,
                rn.viewCount
            )
            FROM RoomNotice rn
            JOIN rn.studyMate sm
            WHERE sm.room.id = :roomId
            ORDER BY rn.createdAt DESC
        """;

        List<RoomResponseDto.NoticeDto> notices = entityManager.createQuery(jpql, RoomResponseDto.NoticeDto.class)
                .setParameter("roomId", roomId)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();

        LocalDateTime now = LocalDateTime.now();
        for (RoomResponseDto.NoticeDto notice : notices) {
            notice.setTimeSincePosted(calculateTimeDifference(notice.getCreatedAt(), now));
        }

        return notices;
    }


    private String calculateTimeDifference(LocalDateTime createdAt, LocalDateTime now) {
        long minutes = ChronoUnit.MINUTES.between(createdAt, now);
        if (minutes < 60) return minutes + "분 전";
        long hours = ChronoUnit.HOURS.between(createdAt, now);
        if (hours < 24) return hours + "시간 전";
        long days = ChronoUnit.DAYS.between(createdAt, now);
        if (days < 7) return days + "일 전";
        long weeks = ChronoUnit.WEEKS.between(createdAt, now);
        if (weeks < 4) return weeks + "주 전";
        long months = ChronoUnit.MONTHS.between(createdAt, now);
        if (months < 12) return months + "개월 전";
        long years = ChronoUnit.YEARS.between(createdAt, now);
        return years + "년 전";
    }

    public void incrementViewCount(Long noticeId) {
        String jpql = "UPDATE RoomNotice rn SET rn.viewCount = rn.viewCount + 1 WHERE rn.id = :noticeId";
        entityManager.createQuery(jpql)
                .setParameter("noticeId", noticeId)
                .executeUpdate();
    }
}