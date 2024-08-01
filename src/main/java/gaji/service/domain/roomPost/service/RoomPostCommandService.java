package gaji.service.domain.roomPost.service;

import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import jakarta.transaction.Transactional;

public interface RoomPostCommandService {
    @Transactional
    RoomPost createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto);

}
