package gaji.service.domain.post.service;

import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import org.springframework.data.domain.Slice;


public interface PostQueryService {

    Post findPostByPostId(Long postId);
    Slice<Post> getPostList(Integer lastPopularityScore,
                            Long lastPostId,
                            Integer lastLikeCnt,
                            Integer lastHit,
                            PostTypeEnum postType,
                            Long categoryId,
                            SortType sortType,
                            PostStatusEnum filter,
                            int page,
                            int size);
    Slice<Post> searchPostList();
    Post getPostDetail(Long postId);
    void validPostOwner(Long userId, Post post);
    void validExistsPostLikes(Long userId, Post post);
    void validExistsPostBookmark(Long userId, Post post);
}
