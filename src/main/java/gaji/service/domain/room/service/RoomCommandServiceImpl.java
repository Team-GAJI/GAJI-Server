package gaji.service.domain.room.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.Role;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.repository.AssignmentRepository;
import gaji.service.domain.room.repository.RoomNoticeRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {
    private final RoomRepository roomRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final StudyMateRepository studyMateRepository;
    private final RoomNoticeRepository roomNoticeRepository;



    @Override
    public Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto){
//        // 현재 로그인한 사용자의 정보를 가져옵니다. 추후 주석 해제
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User currentUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_FOUND));
        // 스터디룸 존재 여부 확인
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));

        // 사용자가 해당 스터디룸에 참여하고 있는지 확인
        studyMateRepository.findByUserIdAndRoomId(user.getId(), roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_IN_ROOM));

        // List<String>을 단일 String으로 변환
        String bodyContent = String.join(", ", requestDto.getBodyList());


        Assignment assignment = Assignment.builder()
                .room(room)
                .weeks(requestDto.getWeek())
                .body(bodyContent)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return savedAssignment;
    }

    @Override
    public RoomNotice createNotice(Long roomId, Long userId, RoomRequestDto.RoomNoticeDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_FOUND));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));

        StudyMate studyMate = studyMateRepository.findByUserIdAndRoomId(user.getId(), roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_IN_ROOM));

        if (studyMate.getRole().equals(Role.READER)) {
            RoomNotice notice = RoomNotice.builder()
                    .title(requestDto.getTitle())
                    .body(requestDto.getBody())
                    .room(room)
                    .build();
            return roomNoticeRepository.save(notice);
        } else {
            throw new RestApiException(RoomErrorStatus._USER_NOT_READER_IN_ROOM);
        }
    }
}
