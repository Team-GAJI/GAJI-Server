package gaji.service.domain.post.service;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostQueryService {

    Post findPostByPostId(Long postId);
    Slice<Post> getPostList(Integer lastPopularityScore,
                            Long lastPostId,
                            Integer lastLikeCnt,
                            Integer lastHit,
                            PostTypeEnum postType,
                            CategoryEnum category,
                            SortType sortType,
                            PostStatusEnum filter,
                            int size);
    Post getPostDetail(Long postId);
}
