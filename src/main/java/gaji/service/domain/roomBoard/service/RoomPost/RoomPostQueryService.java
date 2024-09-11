package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPostBookmark;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomPostQueryService {
    List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId);

    List<RoomPostResponseDto.MainPostSummaryDto> getLatestPosts(Long boardId);

    List<RoomPostResponseDto.PostSummaryDto> getNextPosts(Long roomId, Long lastPostId, int size);

    RoomPost findPostById(Long PostId);

    // TODO: id로 roomPost 북마크 조회
    RoomPostBookmark findRoomPostBookmarkByRoomPostAndStudyMate(RoomPost post, StudyMate studyMate);

    PostComment findPostCommentById(Long troublePostId);

    RoomPostResponseDto.RoomPostDetailDTO getPostDetail(Long postId, Long userId, int page, int size);

    Page<RoomPostResponseDto.CommentWithRepliesDTO> getCommentsWithReplies(Long postId, Pageable pageable);

    RoomPost findRoomPostById(Long roomPostId);
}
