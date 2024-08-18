package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

import java.util.List;

public interface RoomTroublePostQueryService {
    TroublePostComment findCommentByCommentId(Long commentId);

    TroublePostComment findTroublePostCommentById(Long troublePostId);

    List<RoomPostResponseDto.TroublePostSummaryDto> getNextTroublePosts(Long boardId, Long lastPostId, int size);
}
