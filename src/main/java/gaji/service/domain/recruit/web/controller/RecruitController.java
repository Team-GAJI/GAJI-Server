package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitQueryService;
import gaji.service.domain.recruit.service.StudyCommentQueryService;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRecruitPosts")
public class RecruitController {

    private final TokenProviderService tokenProviderService;
    private final RecruitCommandService recruitCommandService;
    private final RecruitQueryService recruitQueryService;
    private final StudyCommentQueryService studyCommentQueryService;

    @PostMapping("")
    @Operation(summary = "스터디 모집 게시글 생성 API", description = "스터디 모집 게시글을 생성하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CreateRoomDTO> createRoom(@RequestBody @Valid RecruitRequestDTO.CreateRoomDTO request, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.CreateRoomDTO responseDTO = recruitCommandService.createRoom(request, userId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "스터디 정보 상세 조회 API", description = "스터디 상세 정보를 조회하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.studyDetailDTO> getStudyDetail(@PathVariable Long roomId) {
        RecruitResponseDTO.studyDetailDTO responseDTO = recruitQueryService.getStudyDetail(roomId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/{roomId}/comments")
    @Operation(summary = "스터디 댓글 조회 API", description = "스터디 댓글을 조회하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CommentListDTO> getCommentList(@PathVariable Long roomId) {
        RecruitResponseDTO.CommentListDTO responseDTO = studyCommentQueryService.getCommentList(roomId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/preview")
    @Operation(summary = "스터디 모집 게시글 미리보기 목록 조회 API", description = "모집 게시글 목록을 조회하는 API 입니다.")
    public BaseResponse<RecruitResponseDTO.PreviewListDTO> getPreviewList(
            @RequestParam(required = false) CategoryEnum category,
            @RequestParam(required = false) PreviewFilter filter,
            @RequestParam(defaultValue = "RECENT") SortType sort,
            @RequestParam(required = false) Long lastValue ){

        RecruitResponseDTO.PreviewListDTO responseDTO = recruitQueryService.getPreviewList(category, filter, sort, lastValue);

        return BaseResponse.onSuccess(responseDTO);
    }
}

