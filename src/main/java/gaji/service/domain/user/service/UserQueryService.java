package gaji.service.domain.user.service;


import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
    Slice<Tuple> getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, int size);
    public User getUserDetail(Long userId);
}
