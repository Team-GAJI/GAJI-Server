package gaji.service.domain.roomBoard.repository.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TroublePostCommentRepository extends JpaRepository<TroublePostComment, Long> {
    List<TroublePostComment> findByRoomTroublePostIdAndIsReplyFalse(Long postId);

    @Query("SELECT c FROM TroublePostComment c WHERE c.roomTroublePost.id = :postId AND c.id > :lastCommentId AND c.isReply = false ORDER BY c.id")
    List<TroublePostComment> findMoreComments(@Param("postId") Long postId, @Param("lastCommentId") Long lastCommentId, Pageable pageable);

    @Query("SELECT c FROM TroublePostComment c WHERE c.parentComment.id = :commentId AND c.id > :lastReplyId ORDER BY c.id")
    List<TroublePostComment> findMoreReplies(@Param("commentId") Long commentId, @Param("lastReplyId") Long lastReplyId, Pageable pageable);

}
