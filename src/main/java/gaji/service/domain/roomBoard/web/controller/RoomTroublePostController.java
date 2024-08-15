package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.enums.PostBookmarkStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.TroublePostComment;
import gaji.service.domain.roomBoard.service.RoomPostQueryService;
import gaji.service.domain.roomBoard.service.RoomTroublePostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomTroublePostController {

    private final TokenProviderService tokenProviderService;
    private final RoomTroublePostCommandService roomTroublePostCommandService;
    private final RoomPostQueryService roomPostQueryService;

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
    @Operation(summary = "스터디룸 트러블슈팅 게시글 좋아요 API")
    public BaseResponse<String> postLike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.addLike(postId, userId, roomId);
        return BaseResponse.onSuccess("LIKE");
    }

    @DeleteMapping("/trouble/{roomId}/posts/{postId}/unlike")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 좋아요 취소 API")
    public BaseResponse<String> postUnlike(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long roomId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomTroublePostCommandService.removeLike(postId, userId, roomId);
        return BaseResponse.onSuccess("UNLIKE");
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
    public BaseResponse<RoomPostResponseDto.toWriteCommentDto> addReply(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long commentId,
            @RequestBody @Valid RoomPostRequestDto.RoomTroubleCommentDto requestDto
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        TroublePostComment replyComment = roomTroublePostCommandService.addReply(commentId, userId, requestDto);
        return BaseResponse.onSuccess(RoomPostConverter.toWriteCommentDto(replyComment));
    }

    @GetMapping("/{boardId}/trouble-posts")
    @Operation(summary = "트러블 슈팅 게시글 무한 스크롤 조회", description = "트러블 슈팅 게시글을 무한 스크롤 방식으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public BaseResponse<List<RoomPostResponseDto.TroublePostSummaryDto>> getNextTroublePosts(
            @PathVariable @Parameter(description = "게시판 ID") Long boardId,
            @RequestParam @Parameter(description = "마지막으로 로드된 게시글 ID") Long lastPostId,
            @RequestParam(defaultValue = "10") @Parameter(description = "조회할 게시글 수") int size) {

        List<RoomPostResponseDto.TroublePostSummaryDto> posts =
                roomPostQueryService.getNextTroublePosts(boardId, lastPostId, size);

        return BaseResponse.onSuccess(posts);
    }
}

