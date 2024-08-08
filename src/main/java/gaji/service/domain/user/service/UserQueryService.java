package gaji.service.domain.user.service;


import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Page;


import java.util.List;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
    List<Room> getUserRoomList(Long userId, Integer limit);

}
