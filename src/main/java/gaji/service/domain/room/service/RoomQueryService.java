package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;

public interface RoomQueryService {

    Room findRoomById(Long roomId);

    RoomEvent findRoomEventByRoomIdAndWeeks(Long roomId, Integer weeks);
}
