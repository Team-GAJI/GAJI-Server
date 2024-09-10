package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;

public interface PostCommonQueryService {
    PostComment findCommentByCommentId(Long commentId);
}
