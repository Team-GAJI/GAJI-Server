package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;

public interface CommentService {

    Comment saveNewComment(Comment comment);
    Comment findByCommentId(Long commentId);
}
