package gaji.service.domain.room.web.dto;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
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
        private String description;
        private LocalDate scheduleTime;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean isRepeat;
        private boolean complete;
        private Long roomId;
        private String roomTitle;
    }
}
