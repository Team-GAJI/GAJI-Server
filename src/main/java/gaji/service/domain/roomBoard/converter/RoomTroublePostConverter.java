package gaji.service.domain.roomBoard.converter;

import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

public class RoomTroublePostConverter {

    public static RoomPostResponseDto.toWriteCommentDto toWriteCommentIdDto(TroublePostComment newComment) {
        return RoomPostResponseDto.toWriteCommentDto.builder()
                .commentId(newComment.getId())
                .build();
    }
}
