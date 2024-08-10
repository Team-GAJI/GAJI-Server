package gaji.service.domain.user.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.room.repository.RoomCustomRepository;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final RoomCustomRepository roomCustomRepository;

    @Override
    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));
    }

    @Override
    public List<Tuple> getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, Integer limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));

        List<Tuple> roomList;

        if(type == RoomTypeEnum.ONGOING) {
            roomList = roomCustomRepository.findAllOngoingRoomsByUser(user, cursorDate, cursorId, limit);
        }
        else{
            roomList = roomCustomRepository.findAllEndedRoomsByUser(user, cursorDate, cursorId, limit);
        }

        return roomList;
    }
}
