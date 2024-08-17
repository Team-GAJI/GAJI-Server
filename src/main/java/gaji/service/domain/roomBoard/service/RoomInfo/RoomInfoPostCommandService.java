package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

public interface RoomInfoPostCommandService {
    RoomPostResponseDto.toCreateRoomInfoPostIdDTO createRoomInfoPostIdDTO(Long roomId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto);

    void updateInfoPost(Long postId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto);

    void deleteInfoPost(Long postId, Long userId);

    RoomPostResponseDto.toWriteCommentDto writeCommentOnInfoPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request);
}
