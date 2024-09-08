package gaji.service.domain.user.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.enums.UserActive;
import gaji.service.domain.post.repository.CommunityPostJpaRepository;
import gaji.service.domain.post.service.CommunityPostQueryService;
import gaji.service.domain.room.repository.RoomCustomRepository;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final RoomQueryService roomQueryService;
    private final CommunityPostQueryService communityPostQueryService;

    @Override
    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    // TODO: ID에 따른 사용자 엔티티 반환
    @Override
    public User findUserById(Long userId) {

        // 존재하는 유저인지 검증
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        // 활성화된 유저인지 검증
        if (user.getStatus().equals(UserActive.IN_ACTIVE)) {
            throw new RestApiException(UserErrorStatus._USER_IS_INACTIVE_);
        }

        return user;
    }

    // TODO: 사용자의 모든 스터디룸을 반환
    @Override
    public Slice<Tuple> getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        // 커서값을 입력했다면 커서 설정
        cursorDate = cursorDate == null ? LocalDate.now() : cursorDate;
        cursorId = cursorId == null ? 0 : cursorId;

        PageRequest pageRequest = PageRequest.of(0, size);

        return roomQueryService.getRoomByUserAndType(user, cursorDate, cursorId, pageRequest, type);
    }

    // TODO: 선택한 타입에 해당하는 사용자의 게시글을 반환
    @Override
    public Slice<Tuple> getUserPostList(Long userId, LocalDateTime cursorDateTime, PostTypeEnum type, int size) {
        User user = findUserById(userId);

        // 커서값을 입력했다면 커서 설정
        cursorDateTime = cursorDateTime == null ? LocalDateTime.now() : cursorDateTime;
        type = type == null ? PostTypeEnum.PROJECT : type;

        PageRequest pageRequest = PageRequest.of(0, size);

        return communityPostQueryService.getAllPostByUserAndType(user, cursorDateTime, pageRequest, type);
    }

    @Override
    public User findByUsernameId(String usernameId) {
        return userRepository.findByUsernameId(usernameId);
    }
}
