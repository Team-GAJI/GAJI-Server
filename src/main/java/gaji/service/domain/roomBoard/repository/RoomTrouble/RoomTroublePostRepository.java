package gaji.service.domain.roomBoard.repository.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTroublePostRepository extends JpaRepository<RoomTroublePost, Long> {
    @Query("SELECT new gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto$TroublePostSummaryDto(" +
            "r.id, r.title, r.studyMate.user.nickname, r.createdAt, r.viewCount, SIZE(r.troublePostCommentList)) " +
            "FROM RoomTroublePost r " +
            "WHERE r.roomBoard.id = :boardId AND r.id < :lastPostId " +
            "ORDER BY r.createdAt DESC")
    List<RoomPostResponseDto.TroublePostSummaryDto> findTroublePostSummariesForInfiniteScroll(
            @Param("boardId") Long boardId,
            @Param("lastPostId") Long lastPostId,
            Pageable pageable);
}
