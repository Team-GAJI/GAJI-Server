package gaji.service.domain.user.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
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
}
