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
}