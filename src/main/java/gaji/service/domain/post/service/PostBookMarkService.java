package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Post;

public interface PostBookMarkService {

    boolean existsByUserAndPost(Long userId, Post post);
}
