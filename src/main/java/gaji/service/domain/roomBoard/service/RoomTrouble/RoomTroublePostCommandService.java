package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

public interface RoomTroublePostCommandService {
    RoomPostResponseDto.toWriteCommentDto writeCommentOnTroublePost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request);

    RoomPostResponseDto.toCreateRoomTroublePostIdDTO createRoomTroublePost(Long roomId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto);

    void addLike(Long postId, Long userId, Long roomId);

    void removeLike(Long postId, Long userId, Long roomId);

    void deletePost(Long postId, Long userId);

    void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto);

    void updateComment(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto);

    void deleteComment(Long commentId, Long userId);

    void addBookmark(Long postId, Long userId, Long roomId);

    void removeBookmark(Long postId, Long userId, Long roomId);

    RoomPostResponseDto.toWriteCommentDto addReply(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto request);

    //답글 삭제
    void deleteReply(TroublePostComment reply);

    void deleteCommentAndReplies(TroublePostComment comment);
}