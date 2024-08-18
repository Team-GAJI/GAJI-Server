package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

import java.util.List;

public interface RoomInfoPostQueryService {
    RoomInfoPost findInfoPostById(Long PostId);

    InfoPostComment findCommentByCommentId(Long commentId);

    InfoPostComment findPostCommentById(Long troublePostId);

    List<RoomPostResponseDto.InfoPostSummaryDto> getNextPosts(Long boardId, Long lastPostId, int size);
}
