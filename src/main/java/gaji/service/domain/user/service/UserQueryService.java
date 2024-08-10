package gaji.service.domain.user.service;


import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
    List<Tuple> getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, Integer limit);

}
