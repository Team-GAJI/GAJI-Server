package gaji.service.domain.post.service;

import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.repository.CommentRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment saveNewComment(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._COMMENT_NOT_FOUND));
    }
}
