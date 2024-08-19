package gaji.service.domain.post.service;

import gaji.service.domain.post.code.CommunityCommentErrorStatus;
import gaji.service.domain.post.converter.CommunityPostConverter;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.repository.CommunityCommentJpaRepository;
import gaji.service.domain.post.web.dto.CommunityPostRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityCommentJpaRepository communityCommentJpaRepository;
    private final CommunityPostQueryService communityPostQueryService;

    @Override
    @Transactional
    public CommunityComment saveNewComment(CommunityComment newComment) {
        return communityCommentJpaRepository.save(newComment);
    }

    @Override
    @Transactional
    public CommunityComment createCommentByCheckParentCommentIdIsNull(Long parentCommentId, CommunityPostRequestDTO.WriteCommentRequestDTO request, User findUser, CommnuityPost findPost) {
        if (parentCommentId != null) {
            CommunityComment parentComment = findByCommentId(parentCommentId);
            return CommunityPostConverter.toComment(request, findUser, findPost, parentComment);
        } else {
            return CommunityPostConverter.toComment(request, findUser, findPost, null);
        }
    }

    @Override
    @Transactional
    public void hardDeleteComment(CommunityComment comment) {
        communityCommentJpaRepository.delete(comment);
    }

    @Override
    public Slice<CommunityComment> getCommentListByPost(Long postId, Integer lastGroupNum, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        CommnuityPost findPost = communityPostQueryService.findPostByPostId(postId);
        return communityCommentJpaRepository.findBySliceAndPostFetchJoinWithUser(lastGroupNum, findPost, pageRequest);
    }

    @Override
    public CommunityComment findByCommentId(Long commentId) {
        return communityCommentJpaRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CommunityCommentErrorStatus._COMMENT_NOT_FOUND));
    }

    @Override
    public boolean isCommentWriter(Long userId, CommunityComment comment) {
        return comment.getUser().getId().equals(userId);
    }

    @Override
    public void validCommentWriter(Long userId, CommunityComment comment) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new RestApiException(CommunityCommentErrorStatus._NOT_AUTHORIZED);
        }
    }
}
