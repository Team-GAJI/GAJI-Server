package gaji.service.domain.room.web.dto;

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
    public static class RoomNoticeDto{
        String title;
        String body;
        Long roomId;
    }

}
