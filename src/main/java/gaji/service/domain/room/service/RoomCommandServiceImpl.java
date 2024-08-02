package gaji.service.domain.room.service;

import com.amazonaws.services.ec2.model.EbsBlockDevice;
import gaji.service.domain.User;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Event;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.AssignmentRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {
    private final RoomRepository roomRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final StudyMateRepository studyMateRepository;



    @Override
    public Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto){
//        // 현재 로그인한 사용자의 정보를 가져옵니다. 추후 주석 해제
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User currentUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));


        User user =confirmUser(userId);
        Room room = confirmRoom(roomId);
        confirmStudyMate(roomId, user.getId());

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
    public Event createEventManagement(Long roomId, Long userId, RoomRequestDto.EventManagementDto requestDto) {

        User user =confirmUser(userId);
        Room room = confirmRoom(roomId);
        confirmStudyMate(roomId, user.getId());

        return  new
    }

    public User confirmUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_FOUND));
    }

    public Room confirmRoom(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));
    }


    public void confirmStudyMate(Long roomId, Long userId){
        studyMateRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));
    }
}
