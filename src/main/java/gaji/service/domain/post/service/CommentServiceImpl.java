package gaji.service.domain.post.service;

import gaji.service.domain.post.code.CommentErrorStatus;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.CommentJpaRepository;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentJpaRepository commentRepository;
    private final PostQueryService postQueryService;

    @Override
    @Transactional
    public Comment saveNewComment(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Override
    @Transactional
    public void hardDeleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Slice<Comment> getCommentListByPost(Long postId, Integer lastGroupNum, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Post findPost = postQueryService.findPostByPostId(postId);
        return commentRepository.findBySliceAndPostFetchJoinWithUser(lastGroupNum, findPost, pageRequest);
    }

    @Override
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CommentErrorStatus._COMMENT_NOT_FOUND));
    }

    @Override
    public void validCommentOwner(Long userId, Comment comment) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new RestApiException(CommentErrorStatus._NOT_OWNER);
        }
    }
}
