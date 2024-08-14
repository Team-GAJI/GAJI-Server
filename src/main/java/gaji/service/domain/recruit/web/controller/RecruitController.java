package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.web.dto.PostResponseDTO;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
    public BaseResponse<RecruitResponseDTO.CreateRoomDTO> createRoom(
            @RequestBody @Valid RecruitRequestDTO.CreateRoomDTO request,
            @RequestHeader("Authorization") String authorizationHeader) {
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

    @PostMapping("/{roomId}/likes")
    @Operation(summary = "스터디 모집 게시글 좋아요 API", description = "스터디 모집 게시글 좋아요 누르는 API입니다.")
    public BaseResponse<RecruitResponseDTO.StudyLikesIdDTO> likeStudy(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.StudyLikesIdDTO responseDTO = recruitCommandService.likeStudy(userId, roomId);
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
}

