package gaji.service.domain.roomPost.converter;

import gaji.service.domain.User;
import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;

import static gaji.service.domain.post.converter.PostConverter.getInitialPostStatus;

public class RoomPostConverter {

    public static RoomPost toPost(RoomPostRequestDto.RoomPostDto requestDto, User user) {
         RoomPost roomPost = RoomPost.builder()
                 .user(user)
                 .title(requestDto.getTitle())
                 .body(requestDto.getBody())
                 .type(requestDto.getType())
                 .status(getInitialPostStatus(requestDto.getType()))
                 .build();
    }


}
