package gaji.service.domain.roomBoard.repository;

import gaji.service.domain.roomBoard.entity.RoomTroublePost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface RoomTroublePostRepository extends JpaRepository<RoomTroublePost, Long> {
    @Query("SELECT new gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto$TroublePostSummaryDto(" +
            "r.id, r.title, r.studyMate.user.id, r.createdAt, r.viewCount, SIZE(r.troublePostCommentList)) " +
            "FROM RoomTroublePost r " +
            "WHERE r.roomBoard.id = :boardId " +
            "ORDER BY r.createdAt DESC")
    List<RoomPostResponseDto.TroublePostSummaryDto> findTroublePostSummaries(@Param("boardId") Long boardId, PageRequest pageRequest);

}
