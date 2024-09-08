package gaji.service.domain.room.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface RoomQueryService {

    Room findRoomById(Long roomId);

    Slice<Tuple> getRoomByUserAndType(User user, LocalDate cursorDate, Long cursorId, Pageable pageable, RoomTypeEnum type);

    RoomEvent findRoomEventByRoomIdAndWeeks(Long roomId, Integer weeks);

//    List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size);
//
//    RoomResponseDto.NoticeDto getNoticeDetail(Long roomId, Long noticeId);
//
//    List<RoomResponseDto.NoticeDto> getNextNotices(Long roomId, Long lastNoticeId, int size);

    List<RoomResponseDto.NoticeDto> getNextNotices(Long roomId, Long lastNoticeId, int size);

    @Transactional(readOnly = true)
    RoomResponseDto.WeeklyStudyInfoDTO getWeeklyStudyInfo(Long roomId, Integer weeks);

    List<RoomResponseDto.UserProgressDTO> getUserProgressByRoomEventId(Long roomId, Integer weeks);

    RoomResponseDto.RoomMainDto getMainStudyRoom(Long roomId);

    RoomResponseDto.MainRoomNoticeDto getMainRoomNotice(Long roomId);

    List<String> getConfirmedUserNicknames(Long noticeId);
}
