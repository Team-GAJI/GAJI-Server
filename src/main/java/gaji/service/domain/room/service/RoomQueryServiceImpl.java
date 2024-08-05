package gaji.service.domain.room.service;


import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.repository.RoomEventRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService{
    private final RoomEventRepository roomEventRepository;
    private final RoomRepository roomRepository;

//    @Override
//    public RoomEvent findRoomEventById(Long roomId){
//        return roomEventRepository.findRoomEventById(roomId)
//                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));
//
//    }

    @Override
    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));

    }

    @Override
    public RoomEvent findRoomEventByRoomIdAndWeeks(Long roomId, Integer weeks) {
        return roomEventRepository.findRoomEventByRoomIdAndWeeks(roomId, weeks)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));

    }

}
