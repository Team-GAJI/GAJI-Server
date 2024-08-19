package gaji.service.domain.post.service;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.code.CommunityPostErrorStatus;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.repository.CommunityPostBookmarkRepository;
import gaji.service.domain.post.repository.CommunityPostJpaRepository;
import gaji.service.domain.post.repository.CommunityPostLikesRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostQueryServiceImpl implements CommunityPostQueryService {
    private final CommunityPostJpaRepository communityPostJpaRepository;
    private final CommunityPostLikesRepository postLikesRepository;
    private final CommunityPostBookmarkRepository postBookmarkRepository;

    @Override
    public Slice<CommnuityPost> getPostList(String keyword,
                                   Integer lastPopularityScore,
                                   Long lastPostId,
                                   Integer lastLikeCnt,
                                   Integer lastHit,
                                   PostTypeEnum postType,
                                   Long categoryId,
                                   SortType sortType,
                                   PostStatusEnum postStatus,
                                   int page,
                                   int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return communityPostJpaRepository.findAllFetchJoinWithUser(keyword,
                lastPopularityScore,
                lastPostId,
                lastLikeCnt,
                lastHit,
                postType,
                postStatus,
                categoryId,
                sortType,
                pageRequest);
    }

    @Override
    public CommnuityPost getPostDetail(Long postId) {
        CommnuityPost findPost = communityPostJpaRepository.findByIdFetchJoinWithUser(postId);
        if (findPost == null) {
            throw new RestApiException(CommunityPostErrorStatus._POST_NOT_FOUND);
        }
        findPost.increaseHitCnt();
        findPost.increasePopularityScoreByHit();
        return findPost;
    }

    @Override
    public CommnuityPost findPostByPostId(Long postId) {
        return communityPostJpaRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(CommunityPostErrorStatus._POST_NOT_FOUND));
    }

    @Override
    public boolean isPostWriter(Long userId, CommnuityPost post) {
        return post.getUser().getId().equals(userId);
    }

    @Override
    public void validPostWriter(Long userId, CommnuityPost post) {
        if (!post.getUser().getId().equals(userId)) {
            throw new RestApiException(CommunityPostErrorStatus._NOT_AUTHORIZED);
        }
    }

    @Override
    public void validExistsPostLikes(Long userId, CommnuityPost post) {
        if (postLikesRepository.existsByUserIdAndPost(userId, post)) {
            throw new RestApiException(CommunityPostErrorStatus._ALREADY_EXIST_POST_LIKES);
        }
    }

    @Override
    public void validExistsPostBookmark(Long userId, CommnuityPost post) {
        if (postBookmarkRepository.existsByUserIdAndPost(userId, post)) {
            throw new RestApiException(CommunityPostErrorStatus._ALREADY_EXIST_POST_BOOKMARK);
        }
    }
}
