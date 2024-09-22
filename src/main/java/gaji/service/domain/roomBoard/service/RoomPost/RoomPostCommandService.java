package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPostLikes;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;

public interface RoomPostCommandService {
    RoomPostResponseDto.toCreateRoomPostIdDTO createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto);
    void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomPostDto requestDto);

    void deletePost(Long postId, Long userId);

    RoomPostResponseDto.toWriteCommentDto writeCommentOnPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request);

    void updateComment(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto);

    void deleteComment(Long commentId, Long userId);

    void addLike(Long postId, Long userId, Long roomId);

    void removeLike(Long postId, Long userId, Long roomId);

    void addBookmark(Long postId, Long userId, Long roomId);

    void removeBookmark(Long postId, Long userId, Long roomId);

    RoomPostResponseDto.toWriteCommentDto addReply(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto request);

    void saveRoomPost(RoomPost roomPost);

    RoomPostLikes findLikesByUserIdAndRoomId(RoomPost post, StudyMate studyMate);
}
