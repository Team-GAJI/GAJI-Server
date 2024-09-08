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

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    // TODO: 사용자를 상태를 업데이트
    @Override
    @Transactional
    public User cancleUser(Long userId) {
        User user = updateUserStatus(userId, UserActive.IN_ACTIVE);

        return user;
    }

    public User updateUserStatus(Long userId, UserActive status) {
        User user = userQueryService.findUserById(userId);
        user.updateStatus(status);

        return user;
    }

    // TODO: 사용자의 닉네임을 업데이트
    @Override
    @Transactional
    public User updateUserNickname(Long userIdFromToken, Long userIdFromPathVariable, UserRequestDTO.UpdateNicknameDTO request) {
        User user = userQueryService.findUserById(userIdFromToken);

        // 닉네임을 변경하는 사용자가 본인인지 검증
        if(!user.equals(userQueryService.findUserById(userIdFromPathVariable))){
            throw new RestApiException(UserErrorStatus._USER_IS_NOT_SAME_);
        }

        String newNickname = request.getNickname();

        // 기존에 사용하던 닉네임과 같은 지 검증
        if (user.getNickname()!=null && user.getNickname().equals(newNickname)) {
            throw new RestApiException(UserErrorStatus._NICKNAME_IS_SAME_);
        }

        user.updateNickname(newNickname);

        return user;
    }

    // TODO: 비활성화 상태인 사용자를 DB에서 삭제
    @Override
    @Transactional
    public void hardDeleteInactiveUsers() {
        // 비활성화 사용자 삭제 주기 설정
        LocalDateTime cutoffDate = LocalDateTime.now()
                .minusDays(UserDeletePeriod.ONE_MONTH.getDays());

        userRepository.deleteAllByInactiveTimeBefore(cutoffDate);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
