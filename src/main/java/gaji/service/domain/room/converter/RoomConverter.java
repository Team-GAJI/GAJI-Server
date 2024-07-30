package gaji.service.domain.room.converter;

import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;

public class RoomConverter {

    public static RoomResponseDto.AssignmentDto toAssignmentDto(Assignment assignment) {
        return RoomResponseDto.AssignmentDto.builder()
                .id(assignment.getId())
                .weeks(assignment.getWeeks())
                .body(assignment.getBody())
                .build();
    }
}
