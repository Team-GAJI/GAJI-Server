package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.service.RoomAssignmentServiceImpl;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomController {

    private final RoomAssignmentServiceImpl assignmentService;

    @PostMapping("/assignments/{roomId}")
    @Operation(summary = "스터디룸 과제 등록 API",description = "스터디룸의 과제를 등록하는 API입니다. room의 id가 존재하는지, 스터디에 참혀하고 있는 user인지 검증합니다.")
    public BaseResponse<RoomResponseDto> AssignmentController(@PathVariable Long userId/*하드 코딩용, 추후 삭제*/, @RequestBody @Valid RoomRequestDto.AssignmentDto requestDto, @PathVariable Long roomId){
        //Long userId = getUserIdFromToken(token);
        RoomResponseDto responseDto = assignmentService.createAssignment(roomId, userId, requestDto);
        return BaseResponse.onSuccess(responseDto);
    }

}
