package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;
import org.springframework.data.domain.Slice;


public interface CommentService {

    Comment saveNewComment(Comment comment);
    Comment findByCommentId(Long commentId);
    Slice<Comment> getCommentListByPost(Long postId, Integer lastGroupNum, int size);
}
