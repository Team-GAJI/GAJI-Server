package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.web.dto.RoomResponseDto;

import java.util.List;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomQueryService {

    Room findRoomById(Long roomId);

    RoomResponseDto.RoomMainDto getMainStudyRoom(Long roomId);

    RoomResponseDto.MainRoomNoticeDto getMainRoomNotice(Long roomId);

    RoomEvent findRoomEventByRoomIdAndWeeks(Long roomId, Integer weeks);

    List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size);

    RoomResponseDto.NoticeDto getNoticeDetail(Long roomId, Long noticeId);

    @Transactional(readOnly = true)
    RoomResponseDto.WeeklyStudyInfoDTO getWeeklyStudyInfo(Long roomEventId);

    @Transactional(readOnly = true)
    List<RoomResponseDto.UserProgressDTO> getUserProgressByRoomEventId(Long roomEventId);
}
