package gaji.service.domain.room.service;

import gaji.service.domain.enums.Role;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.NoticeConfirmation;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.repository.*;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.StudyMate;
import gaji.service.domain.studyMate.UserAssignment;
import gaji.service.domain.studyMate.code.StudyMateErrorStatus;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryServiceImpl;
import gaji.service.global.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomCommandServiceImpl implements RoomCommandService {

    private final AssignmentRepository assignmentRepository;

    private final StudyMateRepository studyMateRepository;
    private final RoomEventRepository roomEventRepository;
    private final RoomQueryService roomQueryService;
    private final UserAssignmentRepository userAssignmentRepository;
    private final UserQueryServiceImpl userQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomNoticeRepository roomNoticeRepository;
    private final NoticeConfirmationRepository noticeConfirmationRepository;
    private final RoomQueryRepository roomQueryRepository;


    //과제생성1
    @Override
    public Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto){
//        // 현재 로그인한 사용자의 정보를 가져옵니다. 추후 주석 해제
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User currentUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));


        RoomEvent roomEvent = roomQueryService.findRoomEventByRoomIdAndWeeks(roomId, requestDto.getWeeks());

        // List<String>을 단일 String으로 변환
        String bodyContent = String.join(", ", requestDto.getBodyList());


        Assignment assignment = Assignment.builder()
                .roomEvent(roomEvent)
                .body(bodyContent)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);

        createUserAssignmentsForStudyMembers(savedAssignment);
        return savedAssignment;
    }

    @Override
    public RoomNotice createNotice(Long roomId, Long userId, RoomRequestDto.RoomNoticeDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), room.getId());

        RoomNotice notice = RoomNotice.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .studyMate(studyMate)
                .build();
        return roomNoticeRepository.save(notice);

    }

    // 과제 생성할 때 user에게 할당해주는 메서드
    @Override
    public void createUserAssignmentsForStudyMembers(Assignment assignment) {

        List<StudyMate> studyMates = studyMateRepository.findByRoom(assignment.getRoomEvent().getRoom());
        for (StudyMate studyMate : studyMates) {
            UserAssignment userAssignment = UserAssignment.builder()
                    .user(studyMate.getUser())
                    .assignment(assignment)
                    .isComplete(false)
                    .build();
            userAssignmentRepository.save(userAssignment);
        }
    }

    @Override
    public RoomEvent setStudyPeriod(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyPeriodDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(roomId, user.getId());

        if (!studyMate.getRole().equals(Role.READER)) {
            throw new RestApiException(RoomErrorStatus._USER_NOT_READER_IN_ROOM);
        }

        RoomEvent roomEvent = roomEventRepository.findRoomEventById(roomId)
                .orElse(RoomEvent.builder().room(room).user(user).build());

        RoomEvent updatedRoomEvent = RoomEvent.builder()
                .id(roomEvent.getId())
                .weeks(weeks)
                .room(room)
                .user(user)
                .startTime(requestDto.getStartDate())
                .endTime(requestDto.getEndDate())
                .title(roomEvent.getTitle())
                .description(roomEvent.getDescription())
                .isPublic(roomEvent.isPublic())
                .build();

        return roomEventRepository.save(updatedRoomEvent);
    }

    @Override
    public RoomEvent setStudyDescription(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyDescriptionDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(roomId, user.getId());


        if (!studyMate.getRole().equals(Role.READER)) {
            throw new RestApiException(RoomErrorStatus._USER_NOT_READER_IN_ROOM);
        }

        RoomEvent roomEvent = roomEventRepository.findRoomEventById(roomId)
                .orElse(RoomEvent.builder().room(room).user(user).build());

        RoomEvent updatedRoomEvent = RoomEvent.builder()
                .id(roomEvent.getId())
                .weeks(weeks)
                .room(room)
                .user(user)
                .startTime(roomEvent.getStartTime())
                .endTime(roomEvent.getEndTime())
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .isPublic(roomEvent.isPublic())
                .build();

        return roomEventRepository.save(updatedRoomEvent);
    }

    @Override
    public boolean toggleNoticeConfirmation(Long noticeId, Long userId) {
        RoomNotice roomNotice = roomNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._NOTICE_NOT_FOUND));

        StudyMate studyMate = studyMateRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(StudyMateErrorStatus._USER_NOT_IN_STUDYROOM));

        NoticeConfirmation existingConfirmation = noticeConfirmationRepository
                .findByRoomNoticeIdAndStudyMateId(noticeId, roomNotice.getStudyMate().getId());

        if (existingConfirmation != null) {
            noticeConfirmationRepository.delete(existingConfirmation);
        } else {
            NoticeConfirmation confirmation = NoticeConfirmation.builder()
                    .roomNotice(roomNotice)
                    .studyMate(studyMate)
                    .build();
            noticeConfirmationRepository.save(confirmation);
        }

        // 확인 수 업데이트
        roomQueryRepository.updateConfirmCount(noticeId);

        return existingConfirmation == null; // true if confirmation was added, false if removed

    }

}
