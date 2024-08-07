package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment saveNewComment(Comment comment);
    Comment findByCommentId(Long commentId);
    List<Comment> findAllByPost(Long postId);
}
