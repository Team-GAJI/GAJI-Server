package gaji.service.domain.room.web.dto;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

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
        private LocalDate startTime;
        private LocalDate endTime;
        private boolean allday;
        private Long roomId;
    }
}
