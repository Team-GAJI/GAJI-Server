package gaji.service.domain.roomBoard.web.controller;

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

    @PostMapping("/info/{roomId}/posts/{postId}/like")
    @Operation(summary = "스터디룸 정보나눔 게시글 좋아요 API")
    public BaseResponse<String> postLike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.addLike(postId, userId, roomId);
        return BaseResponse.onSuccess("LIKE");
    }

    @DeleteMapping("/info/{roomId}/posts/{postId}/unlike")
    @Operation(summary = "스터디룸 정보나눔 게시글 좋아요 취소 API")
    public BaseResponse<String> postUnlike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.removeLike(postId, userId, roomId);
        return BaseResponse.onSuccess("UNLIKE");
    }

    @PostMapping("/info/{roomId}/{postId}/bookmark-add")
    @Operation(summary = "정보나눔 게시글 북마크 추가 API")
    public BaseResponse<String> addBookmark(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long roomId,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.addBookmark(postId, userId, roomId);
        return BaseResponse.onSuccess( "북마크가 성공적으로 업데이트 되었습니다.");
    }

    @DeleteMapping("/info/{roomId}/{postId}/bookmark-remove")
    @Operation(summary = "정보나눔 게시글 북마크 제거 API")
    public BaseResponse<String> removeBookmark(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long roomId,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.removeBookmark(postId, userId, roomId);
        return BaseResponse.onSuccess( "북마크가 성공적으로 삭제되었습니다.");
    }
}
