package gaji.service.domain.post.repository;

import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface PostQueryDslRepository {

    Slice<Post> findAllFetchJoinWithUser(Integer lastPopularityScore,
                                         Long lastPostId,
                                         Integer lastLikeCnt,
                                         Integer lastHit,
                                         PostTypeEnum postType,
                                         PostStatusEnum postStatus,
                                         Long categoryId,
                                         SortType sortType,
                                         Pageable pageable);
    Post findByIdFetchJoinWithUser(Long postId);

}

