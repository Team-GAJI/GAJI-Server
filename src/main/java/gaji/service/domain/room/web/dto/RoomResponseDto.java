package gaji.service.domain.room.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public static class RoomMainDto {
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate recruitStartDate;
        private LocalDate recruitEndDate;
        private Long daysLeftForRecruit;
        private List<String> hashtags;
        private Long applicantCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainRoomNoticeDto {
        private Long latestNoticeId;
        private String latestNoticeBody;
        private List<NoticePreview> noticePreviews;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class NoticePreview {
            private Long id;
            private String title;
            private String body;
        }
    }
}
