package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;

public interface RoomInfoPostQueryService {
    RoomInfoPost findInfoPostById(Long PostId);

    InfoPostComment findCommentByCommentId(Long commentId);

    InfoPostComment findPostCommentById(Long troublePostId);
}
