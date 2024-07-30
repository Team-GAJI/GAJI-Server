package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.Event;
import gaji.service.domain.room.service.RoomCommandServiceImpl;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomController {

    private final RoomCommandServiceImpl assignmentService;

    @PostMapping("/assignments/{roomId}")
    @Operation(summary = "스터디룸 과제 등록 API",description = "스터디룸의 과제를 등록하는 API입니다. room의 id가 존재하는지, 스터디에 참혀하고 있는 user인지 검증합니다.")
    public BaseResponse<RoomResponseDto.AssignmentDto> AssignmentController(@PathVariable Long userId/*하드 코딩용, 추후 삭제*/, @RequestBody @Valid RoomRequestDto.AssignmentDto requestDto, @PathVariable Long roomId){
        //Long userId = getUserIdFromToken(token);
        Assignment assignment = assignmentService.createAssignment(roomId, userId, requestDto);
        return BaseResponse.onSuccess(RoomConverter.toAssignmentDto(assignment));
    }

    @PostMapping("/schedule/{roomId}/{userId}")
    @Operation(summary = "스터디룸 일정 등록 API", description = "스터디룸의 일정을 등록하는 API입니다. room의 id가 존재하는지, user가 스터디에 속해 있는지 검증합니다.")
    public BaseResponse<List<RoomResponseDto.EventDto>> EventController(
            @PathVariable Long userId /*하드 코딩용, 추후 삭제*/,
            @PathVariable Long roomId,
            @RequestBody @Valid RoomRequestDto.EventDto requestDto) {

        List<Event> events = assignmentService.createEvent(roomId, userId, requestDto);
        List<RoomResponseDto.EventDto> eventDtos = events.stream()
                .map(RoomConverter::toEventDto)
                .collect(Collectors.toList());

        return BaseResponse.onSuccess(eventDtos);
    }
}