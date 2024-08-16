package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Slice;


public interface CommentService {

    Comment saveNewComment(Comment comment);
    Comment createCommentByCheckParentCommentIdIsNull(Long parentCommentId, PostRequestDTO.WriteCommentDTO request, User findUser, Post findPost);
    void hardDeleteComment(Comment comment);
    Comment findByCommentId(Long commentId);
    Slice<Comment> getCommentListByPost(Long postId, Integer lastGroupNum, int page, int size);
    void validCommentOwner(Long userId, Comment comment);

}
