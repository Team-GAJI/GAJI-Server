package gaji.service.domain.user.service;

import gaji.service.domain.User;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    public User findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));
    }
}
