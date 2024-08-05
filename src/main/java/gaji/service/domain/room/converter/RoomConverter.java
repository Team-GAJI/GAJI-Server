package gaji.service.domain.room.converter;

import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;

public class RoomConverter {

    public static RoomResponseDto.AssignmentDto toAssignmentDto(Assignment assignment) {
        return RoomResponseDto.AssignmentDto.builder()
                .id(assignment.getId())
                .body(assignment.getBody())
                .build();
    }

    public static RoomResponseDto.RoomNoticeDto toRoomNoticeDto(RoomNotice roomNotice) {
        return RoomResponseDto.RoomNoticeDto.builder()
                .title(roomNotice.getTitle())
                .body(roomNotice.getBody())
                .roomId(roomNotice.getRoom().getId())
                .build();
    }
}
