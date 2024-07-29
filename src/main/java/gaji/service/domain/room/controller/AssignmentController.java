package gaji.service.domain.room.controller;

import gaji.service.domain.studyMate.Assignment;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("//api/studyRooms")
public class AssignmentController {

    private final AssignmentService assignmentService;
    @PostMapping("/assignments")
    @Operation(summary = "스터디룸 과제 등록 API",description = "스터디룸의 과제를 등록하는 API입니다. room의 id가 존재하는지, 등록하는 회원이 READER인지 검증합니다.")
    public ResponseEntity<AssignmentResponseDto> AssignmentController(@RequestBody @Valid AssignmentRequestDto requestDto){

        AssignmentResponseDto responseDto = assignmentService.creatAssignment(roomId, requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
