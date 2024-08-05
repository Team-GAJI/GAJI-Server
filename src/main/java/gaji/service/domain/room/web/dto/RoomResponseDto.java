package gaji.service.domain.room.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class StudyRoomInfoDTO {
        private String name;
        private List<String> hashtags;
        private LocalDate recruitmentEndDate;
        private long daysUntilDeadline;
        private long applicantCount;
    }
}
