package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.service.RoomInfo.RoomInfoPostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomInfoPostController {
    private final TokenProviderService tokenProviderService;
    private final RoomInfoPostCommandService roomInfoPostCommandService;

    @PostMapping("/info/{roomId}")
    @Operation(summary = "스터디룸 정보나눔 게시판 등록 API")
    public BaseResponse<RoomPostResponseDto.toCreateRoomInfoPostIdDTO> StudyRoomInfoPostController(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomInfoPostDto requestDto,
            @PathVariable Long roomId
    ){

        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toCreateRoomInfoPostIdDTO roomInfoPostIdDTO = roomInfoPostCommandService.createRoomInfoPostIdDTO(roomId, userId, requestDto);
        return BaseResponse.onSuccess(roomInfoPostIdDTO);
    }

    @PutMapping("/info/{postId}")
    @Operation(summary = "스터디룸 정보나눔 게시글 업데이트 API")
    public BaseResponse<String> updatePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomInfoPostDto requestDto,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.updateInfoPost(postId, userId,requestDto);
        return BaseResponse.onSuccess( "게시글이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/info/{postId}")
    @Operation(summary = "스터디룸 정보나눔 게시글 삭제 API")
    public BaseResponse<String> deletePost(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.deleteInfoPost(postId, userId);
        return BaseResponse.onSuccess( "게시글이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/info/{postId}/comments")
    @Operation(summary = "스터디룸 정보나눔 댓글 등록 API")
    public BaseResponse<RoomPostResponseDto.toWriteCommentDto> writeCommentOnInfoPost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long postId
    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toWriteCommentDto newComment = roomInfoPostCommandService.writeCommentOnInfoPost(userId, postId, requestDto);
        return BaseResponse.onSuccess(newComment);
    }

    @PutMapping("/info/comments/{commentId}")
    @Operation(summary = "스터디룸 정보나눔 댓글 업데이트 API")
    public BaseResponse<String> updateComment(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.updateComment(commentId, userId,requestDto);
        return BaseResponse.onSuccess( "댓글이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/info/comments/{commentId}")
    @Operation(summary = "스터디룸 정보나눔 게시글 댓글 삭제 API")
    public BaseResponse<String> deleteComment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.deleteComment(commentId, userId);
        return BaseResponse.onSuccess( "댓글이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/info/comments/{commentId}/replies")
    @Operation(summary = "정보나눔 게시글 댓글의 답글 작성 API")
    public BaseResponse<RoomPostResponseDto.toWriteCommentDto> addReply(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        InfoPostComment replyComment = roomInfoPostCommandService.addReply(commentId, userId, requestDto);
        return BaseResponse.onSuccess(RoomPostConverter.toWriteInfoPostCommentDto(replyComment));
    }
}
