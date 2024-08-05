package gaji.service.domain.room.repository;

import gaji.service.domain.room.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomQueryRepository {

    public Room getMainRoom(Room room){
        String jpql = "select room from Room room" +

    }
}
