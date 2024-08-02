package gaji.service.domain.room.web.dto;

import gaji.service.domain.studyMate.Assignment;
import lombok.*;

public class RoomResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignmentDto{
        Long id;
        Integer weeks;
        String body;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDto{
        Long id;
        Integer weeks;
    }

}
