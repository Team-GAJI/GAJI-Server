package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.post.converter.CommentConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.web.dto.CommunityPostCommentResponseDTO;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitQueryService;
import gaji.service.domain.recruit.service.StudyCommentCommandService;
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
    private final StudyCommentCommandService studyCommentCommandService;
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

    @PostMapping("/{roomId}/comments")
    @Operation(summary = "스터디 댓글 작성 API", description = "스터디 댓글을 작성하는 API입니다. 대댓글은 parentCommentId를 받아 작성합니다.")
    public BaseResponse<RecruitResponseDTO.WriteCommentDTO> writeComment(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable @Min(value = 1, message = "roomId는 1 이상 이어야 합니다.") Long roomId,
            @RequestParam(required = false) @Min(value = 1, message = "parentCommentId는 1 이상 이어야 합니다.") Long parentCommentId,
            @RequestBody @Valid RecruitRequestDTO.WriteCommentDTO request) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        RecruitResponseDTO.WriteCommentDTO responseDTO =
                studyCommentCommandService.writeComment(userId, roomId, parentCommentId, request);
        return BaseResponse.onSuccess(responseDTO);
    }
}

