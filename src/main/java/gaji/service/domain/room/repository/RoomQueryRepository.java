package gaji.service.domain.room.repository;

import gaji.service.domain.room.web.dto.RoomResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
                CASE
                    WHEN FUNCTION('TIMESTAMPDIFF', 'MINUTE', rn.createdAt, CURRENT_TIMESTAMP) < 60 THEN 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'MINUTE', rn.createdAt, CURRENT_TIMESTAMP), '분 전')
                    WHEN FUNCTION('TIMESTAMPDIFF', 'HOUR', rn.createdAt, CURRENT_TIMESTAMP) < 24 THEN 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'HOUR', rn.createdAt, CURRENT_TIMESTAMP), '시간 전')
                    WHEN FUNCTION('TIMESTAMPDIFF', 'DAY', rn.createdAt, CURRENT_TIMESTAMP) < 7 THEN 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'DAY', rn.createdAt, CURRENT_TIMESTAMP), '일 전')
                    WHEN FUNCTION('TIMESTAMPDIFF', 'WEEK', rn.createdAt, CURRENT_TIMESTAMP) < 4 THEN 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'WEEK', rn.createdAt, CURRENT_TIMESTAMP), '주 전')
                    WHEN FUNCTION('TIMESTAMPDIFF', 'MONTH', rn.createdAt, CURRENT_TIMESTAMP) < 12 THEN 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'MONTH', rn.createdAt, CURRENT_TIMESTAMP), '개월 전')
                    ELSE 
                        CONCAT(FUNCTION('TIMESTAMPDIFF', 'YEAR', rn.createdAt, CURRENT_TIMESTAMP), '년 전')
                END
            )
            FROM RoomNotice rn
            JOIN rn.studyMate sm
            WHERE sm.room.id = :roomId
            ORDER BY rn.createdAt DESC
        """;

        return entityManager.createQuery(jpql, RoomResponseDto.NoticeDto.class)
                .setParameter("roomId", roomId)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}