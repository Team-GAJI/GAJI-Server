package gaji.service.domain.room.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.Role;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.AssignmentRepository;
import gaji.service.domain.room.repository.RoomEventRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.repository.UserAssignmentRepository;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.StudyMate;
import gaji.service.domain.studyMate.UserAssignment;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.domain.user.service.UserQueryServiceImpl;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {

    private final AssignmentRepository assignmentRepository;

    private final StudyMateRepository studyMateRepository;
    private final RoomEventRepository roomEventRepository;
    private final RoomQueryService roomQueryService;
    private final UserAssignmentRepository userAssignmentRepository;
    private final UserQueryServiceImpl userQueryService;
    private final StudyMateQueryService studyMateQueryService;


    @Override
    public Assignment createAssignment(Long roomEventId, Long userId, RoomRequestDto.AssignmentDto requestDto){

        RoomEvent roomEvent = roomQueryService.findRoomEventById(roomEventId);

        // List<String>을 단일 String으로 변환
        String bodyContent = String.join(", ", requestDto.getBodyList());


        Assignment assignment = Assignment.builder()
                .roomEvent(roomEvent)
                .body(bodyContent)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return savedAssignment;
    }

    @Override
    public void createUserAssignmentsForStudyMembers(Room room, Assignment assignment) {

        List<StudyMate> studyMates = studyMateRepository.findByRoom(room);
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


}
