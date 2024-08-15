package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;
import org.springframework.data.domain.Slice;


public interface CommentService {

    Comment saveNewComment(Comment comment);
    void hardDeleteComment(Comment comment);
    Comment findByCommentId(Long commentId);
    Slice<Comment> getCommentListByPost(Long postId, Integer lastGroupNum, int size);
    void validCommentOwner(Long userId, Comment comment);
}
