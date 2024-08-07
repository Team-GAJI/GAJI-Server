package gaji.service.domain.post.service;

import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostJpaRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService {
    private final PostJpaRepository postRepository;

    @Override
    public List<Post> getPostList(PostTypeEnum postType, String category, SortType sortType, PostStatusEnum postStatus) {
        return postRepository.findAllFetchJoinWithUser(postType, postStatus, sortType);
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
}
