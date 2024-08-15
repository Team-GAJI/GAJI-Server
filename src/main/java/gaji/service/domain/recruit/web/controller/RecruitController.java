package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitQueryService;
import gaji.service.domain.recruit.service.StudyCommentQueryService;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-recruit-posts")
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
            @RequestParam(defaultValue = "recent") SortType sort,
            @RequestParam(required = false) @Min(value = 0, message = "lastValue는 0 이상 입니다.") Long lastValue,
            @RequestParam(value = "page", defaultValue = "20") @Min(value = 1, message = "pageSize는 0보다 커야 합니다.") int pageSize){

        RecruitResponseDTO.PreviewListDTO responseDTO = recruitQueryService.getPreviewList(category, filter, sort, lastValue, pageSize);

        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/preview-default")
    @Operation(summary = "스터디 미리보기 목록 조회 기본 페이지 API", description = "스터디 목록 조회 기본 페이지입니다.")
    public BaseResponse<RecruitResponseDTO.DefaultPreviewListDTO> getDefaultPreviewList(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "index는 0 이상 이어야 합니다.") Integer nextCategoryIndex,
            @RequestParam(defaultValue = "true") boolean isFirst,
            @RequestParam(value = "page", defaultValue = "5") @Min(value = 1, message = "pageSize는 0보다 커야 합니다.") int pageSize){

        RecruitResponseDTO.DefaultPreviewListDTO responseDTO = recruitQueryService.getDefaultPreview(isFirst, nextCategoryIndex, pageSize);

        return BaseResponse.onSuccess(responseDTO);
    }
}

