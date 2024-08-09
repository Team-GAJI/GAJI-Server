package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.entity.Assignment;
import gaji.service.domain.studyMate.web.dto.ResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomMainController {

    private final RoomCommandService roomCommandService;
    private final RoomQueryService roomQueryService;
    private final TokenProviderService tokenProviderService;

    @PostMapping("/assignments/{roomId}/{userId}")
    @Operation(summary = "스터디룸 과제 등록 API",description = "스터디룸의 과제를 등록하는 API입니다. room의 id가 존재하는지, 스터디에 참혀하고 있는 user인지 검증합니다.")
    public BaseResponse<RoomResponseDto.AssignmentResponseDto> AssignmentController(
            @RequestBody @Valid RoomRequestDto.AssignmentDto requestDto,
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String authorizationHeader){

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        Assignment assignment = roomCommandService.createAssignment(roomId, userId, requestDto);
        RoomResponseDto.AssignmentResponseDto responseDto = RoomResponseDto.AssignmentResponseDto.of(assignment.getId());
        return BaseResponse.onSuccess(responseDto);
    }

    @PostMapping("/event/{roomId}/{weeks}/period")
    @Operation(summary = "스터디룸 기간 설정 API", description = "스터디룸의 전체 기간을 설정하는 API입니다.")
    public BaseResponse<RoomResponseDto.EventResponseDto> setStudyPeriod(
            @PathVariable Integer weeks,
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid RoomRequestDto.StudyPeriodDto requestDto) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RoomEvent event = roomCommandService.setStudyPeriod(roomId,weeks, userId, requestDto);
        RoomResponseDto.EventResponseDto responseDto = RoomResponseDto.EventResponseDto.of(event.getId());
        return BaseResponse.onSuccess(responseDto);
    }

    @PostMapping("/event/{roomId}/{weeks}/description")
    @Operation(summary = "스터디룸 설명 입력 API", description = "스터디룸에 대한 설명을 입력하는 API입니다.")
    public BaseResponse<RoomResponseDto.EventResponseDto> setStudyDescription(
            @PathVariable @Min(value = 1, message = "Weeks must be at least 1") Integer weeks,
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid RoomRequestDto.StudyDescriptionDto requestDto) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RoomEvent event = roomCommandService.setStudyDescription(roomId, weeks, userId, requestDto);
        RoomResponseDto.EventResponseDto responseDto = RoomResponseDto.EventResponseDto.of(event.getId());

        return BaseResponse.onSuccess(responseDto);
    }

    @PostMapping("/notice/{userAssignmentId}")
    @Operation(summary = "주차별 과제 체크 박스 체크", description = "과제 체크 박스를 클릭하면 과제 완료 .")
    public ResponseEntity<RoomResponseDto.AssignmentProgressResponse> toggleAssignmentCompletion(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userAssignmentId) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RoomResponseDto.AssignmentProgressResponse response = roomCommandService.toggleAssignmentCompletion(userId, userAssignmentId);
        return ResponseEntity.ok(response);
    }

    // 수정 필요
    //특정 스터디룸의 모든 사용자의 진행 상황을 조회합니다
    @GetMapping("/{roomEventId}/progress")
    @Operation(summary = "주차별 과제 진행율", description = "특정 스터디룸의 모든 사용자의 진행 상황을 조회합니다.")
    public ResponseEntity<List<RoomResponseDto.UserProgressDTO>> getStudyMateProgress(@PathVariable Long roomEventId) {
        List<RoomResponseDto.UserProgressDTO> progressList = roomQueryService.getUserProgressByRoomEventId(roomEventId);
        return ResponseEntity.ok(progressList);
    }

    @GetMapping("/events/{roomEventId}/weekly-info")
    @Operation(summary = "주차별 스터디 정보", description = "특정 주차의 스터디 정보를 조회합니다.")
    public ResponseEntity<RoomResponseDto.WeeklyStudyInfoDTO> getWeeklyStudyInfo(@PathVariable Long roomEventId) {
        RoomResponseDto.WeeklyStudyInfoDTO weeklyInfo = roomQueryService.getWeeklyStudyInfo(roomEventId);
        return ResponseEntity.ok(weeklyInfo);
    }

    @GetMapping("/home/{roomId}")
    @Operation(summary = "스터디룸 main 화면 스터디 정보 조회 API")
    public BaseResponse<RoomResponseDto.RoomMainDto> GetRoomMainController(@PathVariable Long roomId){
        return BaseResponse.onSuccess(roomQueryService.getMainStudyRoom(roomId));
    }

    @GetMapping("/roomPost/{roomId}")
    @Operation(summary = "스터디룸 main 화면 공지사항 정보 조회 API")
    public BaseResponse<RoomResponseDto.MainRoomNoticeDto> GetMainRoomNoticeController(@PathVariable Long roomId){
        return BaseResponse.onSuccess(roomQueryService.getMainRoomNotice(roomId));
    }

}
