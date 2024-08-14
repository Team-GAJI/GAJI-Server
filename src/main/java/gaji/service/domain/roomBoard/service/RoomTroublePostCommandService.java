package gaji.service.domain.roomBoard.service;

import gaji.service.domain.roomBoard.entity.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomBoardRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

public interface RoomTroublePostCommandService {
    static TroublePostComment writeCommentOnCommunityPost(Long userId, Long postId, RoomBoardRequestDto.WriteCommentDTO request) {
    }

    RoomPostResponseDto.toCreateRoomTroublePostIdDTO createRoomTroublePost(Long roomId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto);
}
