package gaji.service.domain.user.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.enums.UserActive;
import gaji.service.domain.post.repository.PostJpaRepository;
import gaji.service.domain.room.repository.RoomCustomRepository;
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
    private final RoomCustomRepository roomCustomRepository;
    private final PostJpaRepository postJpaRepository;

    @Override
    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        if (user.getStatus().equals(UserActive.IN_ACTIVE)) {
            throw new RestApiException(UserErrorStatus._USER_IS_INACTIVE_);
        }

        return user;
    }

    @Override
    public User getUserDetail(Long userId) {
        User user = findUserById(userId);
        return user;
    }

    @Override
    public Slice<Tuple> getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        cursorDate = cursorDate == null ? LocalDate.now() : cursorDate;
        cursorId = cursorId == null ? 0 : cursorId;

        PageRequest pageRequest = PageRequest.of(0, size);

        Slice<Tuple> roomList;

        if(type == RoomTypeEnum.ONGOING) {
            roomList = roomCustomRepository.findAllOngoingRoomsByUser(user, cursorDate, cursorId, pageRequest);
        }
        else{
            roomList = roomCustomRepository.findAllEndedRoomsByUser(user, cursorDate, cursorId, pageRequest);
        }

        return roomList;
    }

    @Override
    public Slice<Tuple> getUserPostList(Long userId, LocalDateTime cursorDateTime, PostTypeEnum type, int size) {
        User user = findUserById(userId);

        cursorDateTime = cursorDateTime == null ? LocalDateTime.now() : cursorDateTime;
        type = type == null ? PostTypeEnum.PROJECT : type;

        PageRequest pageRequest = PageRequest.of(0, size);

        Slice<Tuple> postList = postJpaRepository.findAllPostsByUser(user, cursorDateTime, pageRequest, type);

        return postList;
    }
}
