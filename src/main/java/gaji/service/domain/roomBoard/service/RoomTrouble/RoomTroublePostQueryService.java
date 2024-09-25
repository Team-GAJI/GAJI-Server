package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomTroublePostQueryService {

    TroublePostComment findTroublePostCommentById(Long troublePostId);

    List<RoomPostResponseDto.TroublePostSummaryDto> getNextTroublePosts(Long boardId, Long lastPostId, int size);

    RoomPostResponseDto.TroublePostDetailDTO getPostDetail(Long postId, Long userId, int page, int size);

    Page<RoomPostResponseDto.CommentWithRepliesDTO> getCommentsWithReplies(Long postId, Pageable pageable);

    TroublePostComment findPostCommentById(Long commentId);

    RoomTroublePost findTroublePostById(Long postId);
}
