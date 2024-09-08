package gaji.service.domain.post.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.code.CommunityPostErrorStatus;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.repository.CommunityPostBookmarkRepository;
import gaji.service.domain.post.repository.CommunityPostJpaRepository;
import gaji.service.domain.post.repository.CommunityPostLikesRepository;
import gaji.service.domain.user.entity.User;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostQueryServiceImpl implements CommunityPostQueryService {
    private final CommunityPostJpaRepository communityPostJpaRepository;
    private final CommunityPostLikesRepository postLikesRepository;
    private final CommunityPostBookmarkRepository postBookmarkRepository;

    private final CategoryService categoryService;

    @Override
    public Slice<CommnuityPost> getPostList(String keyword,
                                   Integer lastPopularityScore,
                                   Long lastPostId,
                                   Integer lastLikeCnt,
                                   Integer lastHit,
                                   PostTypeEnum postType,
                                   String category,
                                   SortType sortType,
                                   PostStatusEnum postStatus,
                                   int page,
                                   int size) {
        PageRequest pageRequest = PageRequest.of(page, size);


        Long categoryId=null;
        if(category!=null)
        {
            categoryId=categoryService.findAllByCategory(CategoryEnum.fromValue(category)).get(0).getId();
        }

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

    // TODO: 사용자와 게시글 타입에 따라 모든 게시글을 반환
    @Override
    public Slice<Tuple> getAllPostByUserAndType(User user, LocalDateTime cursorDateTime, Pageable pageable, PostTypeEnum type) {
        Slice<Tuple> postList = communityPostJpaRepository.findAllPostsByUser(user, cursorDateTime, pageable, type);

        return postList;
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
