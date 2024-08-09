package gaji.service.domain.room.service;


import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomQueryRepository;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.repository.RoomEventRepository;
import gaji.service.domain.room.repository.RoomQueryRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.roomPost.repository.RoomPostRepository;
import gaji.service.domain.roomPost.service.RoomPostQueryServiceImpl;
import gaji.service.domain.room.repository.WeeklyUserProgressRepository;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.exception.RestApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryServiceImpl implements RoomQueryService {

    @PersistenceContext
    private EntityManager em;

public class RoomQueryServiceImpl implements RoomQueryService{
    private final RoomEventRepository roomEventRepository;
    private final RoomRepository roomRepository;
    private final RoomQueryRepository roomQueryRepository;
    private final RoomPostRepository roomPostRepository;
    private final RoomPostQueryServiceImpl roomPostQueryService;
    private final RoomQueryRepository roomQueryRepository;
    private final WeeklyUserProgressRepository weeklyUserProgressRepository;

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

    @Override
    public RoomResponseDto.RoomMainDto getMainStudyRoom(Long roomId) {

        RoomResponseDto.RoomMainDto mainStudyRoom = roomQueryRepository.getMainStudyRoom(roomId);
        return mainStudyRoom;
    }

    @Override
    public RoomResponseDto.MainRoomNoticeDto getMainRoomNotice(Long roomId){
        return roomQueryRepository.getRoomNotices(roomId);
    }

    @Override
    public List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size) {
        return roomQueryRepository.getNotices(roomId, page, size);
    }

    @Override
    @Transactional(readOnly = false) // readOnly = false로 설정
    public RoomResponseDto.NoticeDto getNoticeDetail(Long roomId, Long noticeId) {
        RoomResponseDto.NoticeDto notice = roomQueryRepository.getNotices(roomId, 1, Integer.MAX_VALUE).stream()
                .filter(n -> n.getId().equals(noticeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Notice not found"));

        // viewCount 증가
        roomQueryRepository.incrementViewCount (noticeId);

        // 증가된 viewCount를 반영하기 위해 notice 객체 업데이트
        notice.setViewCount(notice.getViewCount() + 1);

        return notice;
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponseDto.WeeklyStudyInfoDTO getWeeklyStudyInfo(Long roomEventId) {
        RoomEvent roomEvent = roomEventRepository.findById(roomEventId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));

        return RoomResponseDto.WeeklyStudyInfoDTO.builder()
                .weekNumber(roomEvent.getWeeks())
                .studyPeriod(new RoomResponseDto.StudyPeriodDTO(roomEvent.getStartTime(), roomEvent.getEndTime()))
                .title(roomEvent.getTitle())
                .content(roomEvent.getDescription())
                .build();
    }

    @Override
    public List<RoomResponseDto.UserProgressDTO> getUserProgressByRoomEventId(Long roomEventId) {
        List<WeeklyUserProgressRepository.UserProgressProjection> projections =
                weeklyUserProgressRepository.findProgressByRoomEventId(roomEventId);

        return projections.stream()
                .map(projection -> RoomResponseDto.UserProgressDTO.builder()
                        .name(projection.getName())
                        .progressPercentage(projection.getProgressPercentage())
                        .build())
                .collect(Collectors.toList());
    }

}

