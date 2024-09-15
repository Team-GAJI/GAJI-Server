package gaji.service.domain.user.service;


import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
    UserResponseDTO.GetRoomListDTO getUserRoomList(Long userId, LocalDate cursorDate, Long cursorId, RoomTypeEnum type, int size);
    UserResponseDTO.GetPostListDTO getUserPostList(Long userId, LocalDateTime cursorDateTime, PostTypeEnum type, int size);
    UserResponseDTO.GetUserDetailDTO getUserDetail(Long userId);
    User findByUsernameId(String usernameId);
}
