package gaji.service.domain.room.converter;

import gaji.service.domain.room.entity.Event;
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

    public static RoomResponseDto.EventDto toEventDto(Event event) {
        return RoomResponseDto.EventDto.builder()
                .description(event.getDescription())
                .complete(event.isComplete())
                .repeat(event.isRepeat())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .roomId(event.getRoom().getId())  // room 정보 추가
                .build();
    }
}
