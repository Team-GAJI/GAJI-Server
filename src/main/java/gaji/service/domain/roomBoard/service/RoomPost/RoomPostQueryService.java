package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

import java.util.List;

public interface RoomPostQueryService {
    List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId);

//    List<RoomPostResponseDto.TroublePostSummaryDto> getPaginatedTroublePosts(Long boardId, int page, int size);

    List<RoomPostResponseDto.PostSummaryDto> getNextPosts(Long boardId, Long lastPostId, int size);

    RoomPost findPostById(Long PostId);

    PostComment findCommentByCommentId(Long commentId);

    PostComment findPostCommentById(Long troublePostId);
}
