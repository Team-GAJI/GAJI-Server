package gaji.service.domain.user.service;

import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    UserRepository userRepository;

    @Override
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(UserErrorStatus._USER_NOT_FOUND)
        );
    }
}
