package gaji.service.domain.roomBoard.converter;

import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomTroublePost;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
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

    public static RoomTroublePost toRoomTroublePost(RoomPostRequestDto.RoomTroubloePostDto requestDto, StudyMate studyMate, RoomBoard roomBoard) {
        return RoomTroublePost.builder()
                .studyMate(studyMate)
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .postTime(LocalDateTime.now())
                .roomBoard(roomBoard)
                .build();
    }
    public static RoomPostResponseDto.toCreateRoomTroublePostIdDTO troublePostIdDto(Long id){
        return RoomPostResponseDto.toCreateRoomTroublePostIdDTO.builder()
                .troublePostId(id)
                .build();
    }


}
