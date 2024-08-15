package gaji.service.domain.room.repository.RoomEvent;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;

import java.time.LocalDate;
import java.util.List;

public interface RoomEventQueryDslRepository {

    List<RoomEvent> findByRoomAndDateBetweenStartTimeAndEndTime(Room room, LocalDate date);

}
