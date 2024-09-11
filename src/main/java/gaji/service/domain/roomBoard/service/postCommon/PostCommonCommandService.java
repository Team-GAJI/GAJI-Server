package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;

public interface PostCommonCommandService {
    void savePostComment(PostComment postComment);

    void deleteReply(PostComment reply);

    void deleteCommentAndReplies(PostComment comment);
}
