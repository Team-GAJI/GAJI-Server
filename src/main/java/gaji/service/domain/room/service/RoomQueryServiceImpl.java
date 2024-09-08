package gaji.service.domain.room.service;


import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.repository.*;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.user.entity.User;
import gaji.service.global.converter.DateConverter;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryServiceImpl implements RoomQueryService {

    private final RoomEventRepository roomEventRepository;
    private final RoomRepository roomRepository;
    private final RoomQueryRepository roomQueryRepository;
    private final RoomCustomRepository roomCustomRepository;
    private final WeeklyUserProgressRepository weeklyUserProgressRepository;
    private final NoticeConfirmationRepository noticeConfirmationRepository;
    private final WebInvocationPrivilegeEvaluator privilegeEvaluator;
    private final RoomNoticeRepository roomNoticeRepository;

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
    // TODO: 사용자와 스터디룸 타입에 따라 모든 스터디룸을 반환
    @Override
    public Slice<Tuple> getRoomByUserAndType(User user, LocalDate cursorDate, Long cursorId, Pageable pageable, RoomTypeEnum type) {
        Slice<Tuple> roomList;

        if(type == RoomTypeEnum.ONGOING) {
            roomList = roomCustomRepository.findAllOngoingRoomsByUser(user, cursorDate, cursorId, pageable);
        }
        else{
            roomList = roomCustomRepository.findAllEndedRoomsByUser(user, cursorDate, cursorId, pageable);
        }

        return roomList;
    }

    @Override
    public RoomEvent findRoomEventByRoomIdAndWeeks(Long roomId, Integer weeks) {
        return roomEventRepository.findRoomEventByRoomIdAndWeeks(roomId, weeks)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));
    }

//    @Override
//    public List<RoomResponseDto.NoticeDto> getNotices(Long roomId, int page, int size) {
//        return roomQueryRepository.getNotices(roomId, page, size);
//    }

    @Override
    public List<RoomResponseDto.NoticeDto> getNextNotices(Long roomId, Long lastNoticeId, int size) {
        LocalDateTime lastCreatedAt;
        if (lastNoticeId == 0) {
            lastCreatedAt = LocalDateTime.now();
        } else {
            lastCreatedAt = roomNoticeRepository.findCreatedAtByIdOrEarliest(roomId, lastNoticeId)
                    .orElseThrow(() -> new RestApiException(RoomErrorStatus._NOTICE_NOT_FOUND));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt", "id");
        Pageable pageable = PageRequest.of(0, size, sort);

        List<RoomResponseDto.NoticeDto> notices = roomNoticeRepository.findNoticeSummariesForInfiniteScroll(roomId, lastCreatedAt, pageable);

        LocalDateTime now = LocalDateTime.now();
        for (RoomResponseDto.NoticeDto notice : notices) {
            notice.setTimeSincePosted(DateConverter.convertToRelativeTimeFormat(notice.getCreatedAt()));
        }
        return notices;
    }
    @Override
    @Transactional(readOnly = true)
    public RoomResponseDto.WeeklyStudyInfoDTO getWeeklyStudyInfo(Long roomId, Integer weeks) {
        RoomEvent roomEvent = roomEventRepository.findRoomEventByRoomIdAndWeeks(roomId, weeks)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));

        return RoomResponseDto.WeeklyStudyInfoDTO.builder()
                .weekNumber(roomEvent.getWeeks())
                .studyPeriod(new RoomResponseDto.StudyPeriodDTO(roomEvent.getStartTime(), roomEvent.getEndTime()))
                .title(roomEvent.getTitle())
                .content(roomEvent.getDescription())
                .build();
    }

    @Override
    public List<RoomResponseDto.UserProgressDTO> getUserProgressByRoomEventId(Long roomId, Integer weeks) {
    RoomEvent roomEvent = roomEventRepository.findRoomEventByRoomIdAndWeeks(roomId, weeks)
            .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_EVENT_NOT_FOUND));

    List<WeeklyUserProgressRepository.UserProgressProjection> projections =
                weeklyUserProgressRepository.findProgressByRoomEventId(roomEvent.getId());

        return projections.stream()
                .map(projection -> RoomResponseDto.UserProgressDTO.builder()
                        .nickname(projection.getNickname())
                        .progressPercentage(projection.getProgressPercentage())
                        .build())
                .collect(Collectors.toList());
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
    public List<String> getConfirmedUserNicknames(Long noticeId) {
        return noticeConfirmationRepository.findConfirmedUserNicknamesByNoticeId(noticeId);
    }
}

