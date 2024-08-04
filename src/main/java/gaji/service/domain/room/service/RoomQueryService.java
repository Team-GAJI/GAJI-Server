package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;

public interface RoomQueryService {

    Room findRoomById(Long roomId);
}