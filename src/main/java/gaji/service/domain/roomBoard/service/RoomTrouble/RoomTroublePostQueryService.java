package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;

public interface RoomTroublePostQueryService {
    TroublePostComment findCommentByCommentId(Long commentId);

    TroublePostComment findTroublePostCommentById(Long troublePostId);
}
