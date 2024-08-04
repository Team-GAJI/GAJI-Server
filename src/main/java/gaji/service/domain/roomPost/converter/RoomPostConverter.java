package gaji.service.domain.roomPost.converter;

import gaji.service.domain.roomPost.entity.RoomBoard;
import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import gaji.service.domain.user.entity.User;

import java.time.LocalDateTime;

public class RoomPostConverter {

    public static RoomPost toRoomPost(RoomPostRequestDto.RoomPostDto requestDto, User user, RoomBoard roomBoard) {
         RoomPost roomPost = RoomPost.builder()
                 .user(user)
                 .title(requestDto.getTitle())
                 .body(requestDto.getBody())
                 .postTime(LocalDateTime.now())
                 .roomBoard(roomBoard)
                 .build();
         return roomPost;
    }


}
