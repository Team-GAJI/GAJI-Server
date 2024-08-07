package gaji.service.domain.room.service;


import com.amazonaws.services.kms.model.NotFoundException;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.repository.RoomEventRepository;
import gaji.service.domain.room.repository.RoomQueryRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService{
    private final RoomEventRepository roomEventRepository;
    private final RoomRepository roomRepository;
    private final RoomQueryRepository roomQueryRepository;

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

    public List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size) {
        return roomQueryRepository.getNotices(roomId, page, size);
    }

    public RoomResponseDto.NoticeDto getNoticeDetail(Long roomId, Long noticeId) {
        return roomQueryRepository.getNotices(roomId, 1, Integer.MAX_VALUE).stream()
                .filter(notice -> notice.getId().equals(noticeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Notice not found"));
    }

}
