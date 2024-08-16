package gaji.service.domain.post.service;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostBookmarkRepository;
import gaji.service.domain.post.repository.PostJpaRepository;
import gaji.service.domain.post.repository.PostLikesRepository;
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
public class PostQueryServiceImpl implements PostQueryService {
    private final PostJpaRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostBookmarkRepository postBookmarkRepository;

    @Override
    public Slice<Post> getPostList(Integer lastPopularityScore,
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
        return postRepository.findAllFetchJoinWithUser(lastPopularityScore,
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
    public Slice<Post> searchPostList() {
        return null;
    }

    @Override
    public Post getPostDetail(Long postId) {
        Post findPost = postRepository.findByIdFetchJoinWithUser(postId);
        if (findPost == null) {
            throw new RestApiException(PostErrorStatus._POST_NOT_FOUND);
        }
        findPost.increaseHitCnt();
        findPost.increasePopularityScoreByHit();
        return findPost;
    }

    @Override
    public Post findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._POST_NOT_FOUND));
    }

    @Override
    public void validPostOwner(Long userId, Post post) {
        if (!post.getUser().getId().equals(userId)) {
            throw new RestApiException(PostErrorStatus._NOT_AUTHORIZED);
        }
    }

    @Override
    public void validExistsPostLikes(Long userId, Post post) {
        if (postLikesRepository.existsByUserIdAndPost(userId, post)) {
            throw new RestApiException(PostErrorStatus._ALREADY_EXIST_POST_LIKES);
        }
    }

    @Override
    public void validExistsPostBookmark(Long userId, Post post) {
        if (postBookmarkRepository.existsByUserIdAndPost(userId, post)) {
            throw new RestApiException(PostErrorStatus._ALREADY_EXIST_POST_BOOKMARK);
        }
    }
}
