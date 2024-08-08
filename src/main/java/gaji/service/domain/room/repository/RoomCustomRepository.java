package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.entity.User;

import java.util.List;

public interface RoomCustomRepository {
    List<Room> findTop3OngoingAndTop3EndedRoomsByUser(User user, Integer limit);
}
