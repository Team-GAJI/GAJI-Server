package gaji.service.domain.roomBoard.service;

import gaji.service.domain.enums.PostLikeStatus;
import gaji.service.domain.roomBoard.entity.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import jakarta.transaction.Transactional;

public interface RoomTroublePostCommandService {
    TroublePostComment writeCommentOnTroublePost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request);

    RoomPostResponseDto.toCreateRoomTroublePostIdDTO createRoomTroublePost(Long roomId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto);

    RoomTroublePost findTroublePostById(Long postId);

    PostLikeStatus toggleLike(Long postId, Long userId, Long roomId);

    void deletePost(Long postId, Long userId);

    void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto);

    void updateComment(Long postId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto);

    TroublePostComment findCommentByCommentId(Long commentId);
}