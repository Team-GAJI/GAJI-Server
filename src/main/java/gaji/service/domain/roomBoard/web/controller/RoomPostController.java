package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.service.RoomPost.RoomPostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomPostController {

    private final TokenProviderService tokenProviderService;
    private final RoomPostCommandService roomPostCommandService;

    @PostMapping("/post/{roomId}")
    @Operation(summary = "스터디룸 게시글 등록 API")
    public BaseResponse<RoomPostResponseDto.toCreateRoomPostIdDTO> createRoomPostController(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("roomId") Long roomId,
            @RequestBody RoomPostRequestDto.RoomPostDto requestDto
            ){
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toCreateRoomPostIdDTO toCreateRoomPostIdDTO = roomPostCommandService.createRoomPost(roomId, userId, requestDto);
        return BaseResponse.onSuccess(toCreateRoomPostIdDTO);

    }

    @PutMapping("/post/{postId}")
    @Operation(summary = "스터디룸 게시글 업데이트 API")
    public BaseResponse<String> updatePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomPostDto requestDto,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.updatePost(postId, userId,requestDto);
        return BaseResponse.onSuccess( "게시글이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/post/{postId}")
    @Operation(summary = "스터디룸 게시글 삭제 API")
    public BaseResponse<String> deletePost(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.deletePost(postId, userId);
        return BaseResponse.onSuccess( "게시글이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/post/{postId}/comments")
    @Operation(summary = "스터디룸 게시글 댓글 등록 API")
    public BaseResponse<RoomPostResponseDto.toWriteCommentDto> writeCommentOnPost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long postId
    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toWriteCommentDto newComment = roomPostCommandService.writeCommentOnPost(userId, postId, requestDto);
        return BaseResponse.onSuccess(newComment);
    }

    @PutMapping("/post/comments/{commentId}")
    @Operation(summary = "스터디룸 게시글 댓글 업데이트 API")
    public BaseResponse<String> updateComment(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomTroubleCommentDto requestDto,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.updateComment(commentId, userId,requestDto);
        return BaseResponse.onSuccess( "댓글이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/post/comments/{commentId}")
    @Operation(summary = "스터디룸 게시글 댓글 삭제 API")
    public BaseResponse<String> deleteComment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.deleteComment(commentId, userId);
        return BaseResponse.onSuccess( "댓글이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/post/{roomId}/posts/{postId}/like")
    @Operation(summary = "스터디룸 게시글 좋아요 API")
    public BaseResponse<String> postLike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.addLike(postId, userId, roomId);
        return BaseResponse.onSuccess("LIKE");
    }

    @DeleteMapping("/post/{roomId}/posts/{postId}/unlike")
    @Operation(summary = "스터디룸 게시글 좋아요 취소 API")
    public BaseResponse<String> postUnlike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.removeLike(postId, userId, roomId);
        return BaseResponse.onSuccess("UNLIKE");
    }
}
