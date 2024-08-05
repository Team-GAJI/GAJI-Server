package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.web.dto.RoomResponseDto;

import java.util.List;

public interface RoomQueryService {

    Room findRoomById(Long roomId);

    RoomResponseDto.RoomMainDto getMainStudyRoom(Long roomId);
}
