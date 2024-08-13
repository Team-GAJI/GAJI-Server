package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;

public interface PostLikesService {
    boolean existsByUserAndPost(Long userId, Post post);
}
