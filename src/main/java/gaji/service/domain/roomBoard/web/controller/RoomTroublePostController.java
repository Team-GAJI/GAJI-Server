package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.enums.PostBookmarkStatus;
import gaji.service.domain.enums.PostLikeStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.TroublePostComment;
import gaji.service.domain.roomBoard.service.RoomTroublePostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomTroublePostController {

    private final TokenProviderService tokenProviderService;
    private final RoomTroublePostCommandService roomTroublePostCommandService;

    @PostMapping("/trouble/{roomId}")
    @Operation(summary = "스터디룸 트러블슈팅 게시판 등록 API")
    public BaseResponse<RoomPostResponseDto.toCreateRoomTroublePostIdDTO> AssignmentController(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubloePostDto requestDto,
            @PathVariable Long roomId
    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toCreateRoomTroublePostIdDTO roomTroublePostIdDTO = roomTroublePostCommandService.createRoomTroublePost(roomId, userId, requestDto);
        return BaseResponse.onSuccess(roomTroublePostIdDTO);
    }

    @PostMapping("/trouble/{troublePostId}/comments")
    @Operation(summary = "스터디룸 트러블슈팅 댓글 등록 API")
    public BaseResponse<RoomPostResponseDto.toWriteCommentDto> writeCommentOnTroublePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long troublePostId
    ){
        System.out.println("댓글: " + requestDto.getBody());
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        TroublePostComment newComment = roomTroublePostCommandService.writeCommentOnTroublePost(userId, troublePostId, requestDto);
        return BaseResponse.onSuccess(RoomPostConverter.toWriteCommentDto(newComment));
    }

    @PostMapping("/trouble/{roomId}/posts/{postId}/like")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 좋아요 누르기 API")
    public BaseResponse<PostLikeStatus> toggleLike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        PostLikeStatus status = roomTroublePostCommandService.toggleLike(postId, userId, roomId);
        return BaseResponse.onSuccess(status);
    }

    @DeleteMapping("/trouble/{postId}")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 삭제 API")
    public BaseResponse<String> deletePost(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.deletePost(postId, userId);
        return BaseResponse.onSuccess( "게시글이 성공적으로 삭제되었습니다.");
    }

    @PutMapping("/trouble/{postId}")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 업데이트 API")
    public BaseResponse<String> updatePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomTroubloePostDto requestDto,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.updatePost(postId, userId,requestDto);
        return BaseResponse.onSuccess( "게시글이 성공적으로 업데이트되었습니다.");
    }

    @PutMapping("/trouble/comments/{commentId}")
    @Operation(summary = "스터디룸 트러블슈팅 댓글 업데이트 API")
    public BaseResponse<String> updateComment(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.updateComment(commentId, userId,requestDto);
        return BaseResponse.onSuccess( "댓글이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/trouble/comments/{commentId}")
    @Operation(summary = "스터디룸 트러블슈팅 댓글 삭제 API")
    public BaseResponse<String> deleteComment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.deleteComment(commentId, userId);
        return BaseResponse.onSuccess( "댓글이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/trouble/{roomId}/posts/{postId}/bookmark")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 북마크 누르기 API")
    public BaseResponse<PostBookmarkStatus> toggleBookmark(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        PostBookmarkStatus status = roomTroublePostCommandService.toggleBookmark(postId, userId, roomId);
        return BaseResponse.onSuccess(status);
    }

    @PostMapping("/comments/{commentId}/replies")
    @Operation(summary = "트러블 슈팅 게시글 댓글의 답글 작성 API")
    public BaseResponse<TroublePostComment> addReply(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        TroublePostComment reply = roomTroublePostCommandService.addReply(commentId, userId, requestDto);
        return BaseResponse.onSuccess(reply);
    }
}

