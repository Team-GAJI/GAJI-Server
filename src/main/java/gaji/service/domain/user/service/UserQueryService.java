package gaji.service.domain.user.service;


import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
    Post getUserPost(Long userId);
}
