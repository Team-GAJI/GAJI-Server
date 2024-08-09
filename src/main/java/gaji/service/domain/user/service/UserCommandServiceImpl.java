package gaji.service.domain.user.service;

import gaji.service.domain.enums.UserActive;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.enums.UserDeletePeriod;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.domain.user.web.dto.UserRequestDTO;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User cancleUser(Long userId) {
        User user = updateUserStatus(userId, UserActive.IN_ACTIVE);

        return user;
    }

    public User updateUserStatus(Long userId, UserActive status) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        user.updateStatus(status);

        return user;
    }

    @Override
    @Transactional
    public User updateUserNickname(Long userIdFromToken, Long userIdFromPathVariable, UserRequestDTO.UpdateNicknameDTO request) {
        User user = userRepository.findById(userIdFromToken)
                .orElseThrow(()-> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        if(!user.equals(userRepository.findById(userIdFromPathVariable)
                .orElseThrow(()-> new RestApiException(UserErrorStatus._USER_NOT_FOUND)))){
            throw new RestApiException(UserErrorStatus._USER_IS_NOT_SAME_);
        }

        String newNickname = request.getNickname();

        if (user.getNickname().equals(newNickname)) {
            throw new RestApiException(UserErrorStatus._NICKNAME_IS_SAME_);
        }
        user.updateNickname(newNickname);

        return user;
    }

    @Override
    @Transactional
    public void hardDeleteInactiveUsers() {
        LocalDateTime cutoffDate = LocalDateTime.now()
                .minusDays(UserDeletePeriod.ONE_MONTH.getDays());
        userRepository.deleteAllByInactiveTimeBefore(cutoffDate);
    }
}
