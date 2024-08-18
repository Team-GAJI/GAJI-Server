package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitQueryService;
import gaji.service.domain.recruit.service.StudyCommentCommandService;
import gaji.service.domain.recruit.service.StudyCommentQueryService;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final StudyCommentCommandService studyCommentCommandService;
    private final StudyCommentQueryService studyCommentQueryService;

    @PostMapping("")
    @Operation(summary = "스터디 모집 게시글 생성 성성 API", description = "스터디 모집 게시글을 생성하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CreateRoomResponseDTO> createRoom(
            @RequestBody @Valid RecruitRequestDTO.CreateRoomDTO request,
            @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        return BaseResponse.onSuccess(
                recruitCommandService.createRoom(request, userId)
        );
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "스터디 정보 상세 조회 API", description = "스터디 상세 정보를 조회하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.studyDetailResponseDTO> getStudyDetail(@PathVariable Long roomId) {
        RecruitResponseDTO.studyDetailResponseDTO responseDTO = recruitQueryService.getStudyDetail(roomId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/{roomId}/comments")
    @Operation(summary = "스터디 댓글 조회 API", description = "스터디 댓글을 조회하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CommentListResponseDTO> getCommentList(
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId,
            @RequestParam(required = false) @Min(value = 0, message = "commentOrder는 0 이상 이어야 합니다.") Integer commentOrder,
            @RequestParam(required = false) @Min(value = 0, message = "depth는 0 이상 이어야 합니다.") Integer depth,
            @RequestParam(required = false) @Min(value = 1, message = "commentId는 1 이상 이어야 합니다.") Long commentId,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 1 이상 이어야 합니다.") int size) {
        RecruitResponseDTO.CommentListResponseDTO responseDTO =
                studyCommentQueryService.getCommentList(roomId, commentOrder, depth, commentId, size);
        return BaseResponse.onSuccess(responseDTO);
    }

    @PostMapping("/{roomId}/comments")
    @Operation(summary = "스터디 댓글 작성 API", description = "스터디 댓글을 작성하는 API입니다. 대댓글은 parentCommentId를 받아 작성합니다.")
    public BaseResponse<RecruitResponseDTO.WriteCommentResponseDTO> writeComment(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId,
            @RequestParam(required = false) @Min(value = 1, message = "parentCommentId는 1 이상 이어야 합니다.") Long parentCommentId,
            @RequestBody @Valid RecruitRequestDTO.WriteCommentDTO request) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.WriteCommentResponseDTO responseDTO =
                studyCommentCommandService.writeComment(userId, roomId, parentCommentId, request);
        return BaseResponse.onSuccess(responseDTO);
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "스터디 댓글 삭제 API", description = "스터디 댓글을 삭제하는 API입니다. 댓글의 자식 댓글들도 모두 삭제됩니다.")
    public BaseResponse deleteComment(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "commentId는 1 이상 이어야 합니다.") Long commentId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        studyCommentCommandService.deleteComment(userId, commentId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{roomId}/likes")
    @Operation(summary = "스터디 모집 게시글 좋아요 API", description = "스터디 모집 게시글 좋아요 누르는 API입니다.")
    public BaseResponse<RecruitResponseDTO.StudyLikesIdResponseDTO> likeStudy(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.StudyLikesIdResponseDTO responseDTO = recruitCommandService.likeStudy(userId, roomId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @DeleteMapping("/{roomId}/likes")
    @Operation(summary = "스터디 모집 게시글 좋아요 취소 API", description = "스터디 모집 게시글 좋아요 취소하는 API 입니다.")
    public BaseResponse unLikeStudy(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.")  Long roomId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        recruitCommandService.unLikeStudy(userId, roomId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{roomId}/bookmarks")
    @Operation(summary = "스터디 모집 게시글 북마크 API", description = "스터디 모집 게시글 북마크 누르는 API입니다.")
    public BaseResponse<RecruitResponseDTO.StudyBookmarkIdDTO> bookmarkStudy(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.StudyBookmarkIdDTO responseDTO = recruitCommandService.bookmarkStudy(userId, roomId);
        return BaseResponse.onSuccess(responseDTO);
    }

    @DeleteMapping("/{roomId}/bookmarks")
    @Operation(summary = "스터디 모집 게시글 북마크 취소 API", description = "스터디 모집 게시글 북마크 취소하는 API 입니다.")
    public BaseResponse unBookmarkStudy(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.")  Long roomId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        recruitCommandService.unBookmarkStudy(userId, roomId);
        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/preview")
    @Operation(summary = "스터디 모집 게시글 미리보기 목록 조회 API", description = "모집 게시글 목록을 조회하는 API 입니다.")
    public BaseResponse<RecruitResponseDTO.PreviewListResponseDTO> getPreviewList(
            @RequestParam(required = false) CategoryEnum category,
            @RequestParam(required = false) PreviewFilter filter,
            @RequestParam(defaultValue = "recent") SortType sort,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) @Min(value = 0, message = "lastValue는 0 이상 입니다.") Long lastValue,
            @RequestParam(value = "page", defaultValue = "20") @Min(value = 1, message = "pageSize는 0보다 커야 합니다.") int pageSize){

        RecruitResponseDTO.PreviewListResponseDTO responseDTO = recruitQueryService.getPreviewList(category, filter, sort, query, lastValue, pageSize);
        return BaseResponse.onSuccess(responseDTO);
    }

    @GetMapping("/preview-default")
    @Operation(summary = "스터디 미리보기 목록 조회 기본 페이지 API", description = "스터디 목록 조회 기본 페이지입니다.")
    public BaseResponse<RecruitResponseDTO.DefaultPreviewListResponseDTO> getDefaultPreviewList(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "index는 0 이상 이어야 합니다.") Integer nextCategoryIndex,
            @RequestParam(defaultValue = "true") boolean isFirst,
            @RequestParam(value = "page", defaultValue = "5") @Min(value = 1, message = "pageSize는 0보다 커야 합니다.") int pageSize) {

        RecruitResponseDTO.DefaultPreviewListResponseDTO responseDTO = recruitQueryService.getDefaultPreview(isFirst, nextCategoryIndex, pageSize);
        return BaseResponse.onSuccess(responseDTO);
    }
}

