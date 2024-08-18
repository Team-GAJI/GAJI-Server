package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/study-rooms")
@RequiredArgsConstructor
public class RoomNoticeController {


    private final RoomCommandService roomCommandService;
    private final RoomQueryService roomQueryService;
    private final TokenProviderService tokenProviderService;


    @PostMapping("/notices/{roomId}")
    @Operation(summary = "스터디룸 공지 등록 API",description = "스터디룸의 공지를 등록하는 API입니다. room의 id가 존재하는지 검증합니다.")
    public BaseResponse<RoomResponseDto.RoomNoticeDto> NoticeController(
            @RequestBody @Valid RoomRequestDto.RoomNoticeDto requestDto,
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long roomId) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RoomNotice roomNotice = roomCommandService.createNotice(roomId, userId, requestDto);

        return BaseResponse.onSuccess(RoomConverter.toRoomNoticeDto(roomNotice));
    }

    @GetMapping("/{roomId}/notices")
    @Operation(summary = "스터디룸 공지 목록 조회 API")
    public BaseResponse<RoomResponseDto.NoticeDtoList> getNotices(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<RoomResponseDto.NoticeDto> notices = roomQueryService.getNotices(roomId, page, size);
        return BaseResponse.onSuccess(
                new RoomResponseDto.NoticeDtoList(notices)
        );
    }

//    @GetMapping("/notice/{noticeId}")
//    @Operation(summary = "특정 공지사항을 조회하는 API")
//    public ResponseEntity<RoomResponseDto.NoticeDto> getNoticeDetail(
//            @PathVariable Long roomId,
//            @PathVariable Long noticeId) {
//        RoomResponseDto.NoticeDto notice = roomQueryService.getNoticeDetail(roomId, noticeId);
//        return ResponseEntity.ok(notice);
//    }


    @PostMapping("/notice/{noticeId}/confirm/{userId}")
    @Operation(summary = "스터디룸 공지 확인 버튼 누르기 API", description = "공지사항 확인 상태를 토글합니다.")
    public BaseResponse<RoomResponseDto.IsConfirmedResponse> toggleNoticeConfirmation(
            @PathVariable Long noticeId,
            @RequestHeader("Authorization") String authorizationHeader) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        boolean isConfirmed = roomCommandService.toggleNoticeConfirmation(noticeId,userId);

        return BaseResponse.onSuccess(
                new RoomResponseDto.IsConfirmedResponse(isConfirmed)
        );
    }


}
