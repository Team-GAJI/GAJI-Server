package gaji.service.domain.room.service;

import gaji.service.domain.room.code.PostErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.AssignmentRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.AssignmentRequestDto;
import gaji.service.domain.room.web.dto.AssignmentResponseDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostAssignmentServiceImpl implements PostAssignmentService {
    private final RoomRepository roomRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public AssignmentResponseDto createAssignment(Long roomId, AssignmentRequestDto.AssignmentDto requestDto){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._ROOM_NOT_FOUND));


        // List<String>을 단일 String으로 변환
        String bodyContent = String.join(", ", requestDto.getBodyList());


        Assignment assignment = Assignment.builder()
                .room(room)
                .weeks(requestDto.getWeek())
                .body(bodyContent)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return new AssignmentResponseDto(savedAssignment);
    }

}
