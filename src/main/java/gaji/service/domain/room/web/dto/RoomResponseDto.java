package gaji.service.domain.room.web.dto;

import lombok.*;

public class RoomResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignmentDto{
        Long id;
        String body;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDto {
        Long id;
        Integer weeks;

    }

}
