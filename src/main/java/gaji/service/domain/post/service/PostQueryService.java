package gaji.service.domain.post.service;

import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;

import java.util.List;

public interface PostQueryService {

    Post findPostByPostId(Long postId);
    List<Post> getPostList(PostTypeEnum postType,
                           String category,
                           SortType sortType,
                           PostStatusEnum filter);
    Post getPostDetail(Long postId);
}
