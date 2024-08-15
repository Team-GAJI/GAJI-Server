package gaji.service.domain.roomBoard.service;

import gaji.service.domain.roomBoard.entity.RoomPost;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;

public interface RoomPostCommandService {
    RoomPost createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto);

}
