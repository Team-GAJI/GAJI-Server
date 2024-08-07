package gaji.service.domain.post.repository;

import gaji.service.domain.common.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;

import java.util.List;

public interface PostQueryDslRepository {

    List<Post> findAllFetchJoinWithUser(PostTypeEnum postType, PostStatusEnum postStatus, SortType sortType);
    Post findByIdFetchJoinWithUser(Long postId);

}

