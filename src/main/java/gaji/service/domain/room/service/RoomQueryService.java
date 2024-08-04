package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.RoomEvent;

public interface RoomQueryService {
    RoomEvent findRoomEventById(Long roomId);
}
