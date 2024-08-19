package gaji.service.domain.post.service;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.entity.CommnuityPost;
import org.springframework.data.domain.Slice;


public interface CommunityPostQueryService {

    CommnuityPost findPostByPostId(Long postId);
    Slice<CommnuityPost> getPostList(Integer lastPopularityScore,
                            Long lastPostId,
                            Integer lastLikeCnt,
                            Integer lastHit,
                            PostTypeEnum postType,
                            Long categoryId,
                            SortType sortType,
                            PostStatusEnum filter,
                            int page,
                            int size);
    Slice<CommnuityPost> searchPostList();
    CommnuityPost getPostDetail(Long postId);
    boolean isPostWriter(Long userId, CommnuityPost post);
    void validPostWriter(Long userId, CommnuityPost post);
    void validExistsPostLikes(Long userId, CommnuityPost post);
    void validExistsPostBookmark(Long userId, CommnuityPost post);
}
