package gaji.service.domain.roomBoard.repository.RoomPost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto.*;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomPostRepository extends JpaRepository<RoomPost, Long> {
    @Query("SELECT new gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto$PostSummaryDto(" +
            "r.id, r.title, r.studyMate.user.nickname, r.createdAt, r.viewCount, SIZE(r.postCommentList)) " +
            "FROM RoomPost r " +
            "WHERE r.roomBoard.id = :boardId AND r.id < :lastPostId " +
            "ORDER BY r.createdAt DESC")
    List<RoomPostResponseDto.PostSummaryDto> findPostSummariesForInfiniteScroll(
            @Param("boardId") Long boardId,
            @Param("lastPostId") Long lastPostId,
            Pageable pageable);
}
