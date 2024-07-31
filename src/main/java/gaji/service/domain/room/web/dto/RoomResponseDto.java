package gaji.service.domain.room.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventDto {

        private String description;
        private LocalDate scheduleDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean isRepeat;
        private boolean complete;
        private String roomTitle;
    }
}
