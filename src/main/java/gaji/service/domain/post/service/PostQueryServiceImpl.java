package gaji.service.domain.post.service;

import gaji.service.domain.common.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;

    @Override
    public List<Post> getPostList(PostTypeEnum postType, String category, SortType sortType, PostStatusEnum postStatus) {
        return postRepository.findAllFetchJoinWithUser(postType, postStatus, sortType);
    }

    @Override
    public Post findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._POST_NOT_FOUND));
    }
}
