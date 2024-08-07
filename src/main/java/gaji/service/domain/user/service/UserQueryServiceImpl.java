package gaji.service.domain.user.service;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostRepository;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final PostRepository postRepository; // Service 의존 ? Repo의존? Read작업이라 일단 Repo 사용

    @Override
    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));
    }

    @Override
    public Post getUserPost(Long userId, PostTypeEnum type){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));
        Post post = postRepository.findFirstByUserAndTypeOrderByCreatedAtDesc(user,type)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._POST_NOT_CREATED));

        return post;
    }
}
