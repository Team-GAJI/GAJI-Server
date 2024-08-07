package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.service.RoomCommandServiceImpl;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.global.base.BaseResponse;
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
public class RoomController {

    private final RoomCommandServiceImpl roomCommandService;
    private final RoomQueryService roomQueryService;
    @PostMapping("/assignments/{roomId}/{userId}")
    @Operation(summary = "스터디룸 과제 등록 API",description = "스터디룸의 과제를 등록하는 API입니다. room의 id가 존재하는지, 스터디에 참혀하고 있는 user인지 검증합니다.")
    public BaseResponse<Long> AssignmentController(@PathVariable Long userId/*하드 코딩용, 추후 삭제*/, @RequestBody @Valid RoomRequestDto.AssignmentDto requestDto, @PathVariable Long roomId){
        //Long userId = getUserIdFromToken(token);
        Assignment assignment = roomCommandService.createAssignment(roomId, userId, requestDto);
        return BaseResponse.onSuccess(assignment.getId());
    }

    @PostMapping("/event/{roomId}/{weeks}/{userId}/period")
    @Operation(summary = "스터디룸 기간 설정 API", description = "스터디룸의 전체 기간을 설정하는 API입니다.")
    public BaseResponse<Long> setStudyPeriod(
            @PathVariable Integer weeks,
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody @Valid RoomRequestDto.StudyPeriodDto requestDto
    ) {
        RoomEvent event = roomCommandService.setStudyPeriod(roomId,weeks, userId, requestDto);
        return BaseResponse.onSuccess(event.getId());
    }

    @PostMapping("/event/{roomId}/{weeks}/{userId}/description")
    @Operation(summary = "스터디룸 설명 입력 API", description = "스터디룸에 대한 설명을 입력하는 API입니다.")
    public BaseResponse<Long> setStudyDescription(
            @PathVariable @Min(value = 1, message = "Weeks must be at least 1") Integer weeks,
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody @Valid RoomRequestDto.StudyDescriptionDto requestDto
    ) {
        RoomEvent event = roomCommandService.setStudyDescription(roomId, weeks, userId, requestDto);
        return BaseResponse.onSuccess(event.getId());
    }
    @PostMapping("/notices/{roomId}/{userId}")
    @Operation(summary = "스터디룸 공지 등록 API",description = "스터디룸의 공지를 등록하는 API입니다. room의 id가 존재하는지, 작성자가 Reader인지 검증합니다.")
    public BaseResponse<RoomResponseDto.RoomNoticeDto> NoticeController(@PathVariable Long userId/*하드 코딩용, 추후 삭제*/, @RequestBody @Valid RoomRequestDto.RoomNoticeDto requestDto, @PathVariable Long roomId) {
        RoomNotice roomNotice = roomCommandService.createNotice(roomId, userId, requestDto);
        return BaseResponse.onSuccess(RoomConverter.toRoomNoticeDto(roomNotice));
    }

    @GetMapping("/{roomId}/notices")
    public ResponseEntity<List<RoomResponseDto.NoticeDto>> getNotices(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<RoomResponseDto.NoticeDto> notices = roomQueryService.getNotices(roomId, page, size);
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/{roomId}/notices/{noticeId}")
    public ResponseEntity<RoomResponseDto.NoticeDto> getNoticeDetail(
            @PathVariable Long roomId,
            @PathVariable Long noticeId) {
        RoomResponseDto.NoticeDto notice = roomQueryService.getNoticeDetail(roomId, noticeId);
        return ResponseEntity.ok(notice);
    }


}
