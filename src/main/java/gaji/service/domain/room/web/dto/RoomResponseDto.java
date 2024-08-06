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
        private LocalDate startDay;
        private LocalDate endDay;
        private LocalDate recruitStartDay;
        private LocalDate recruitEndDay;
        private Long daysLeftForRecruit;
        private Long applicantCount;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainRoomNoticeDto {
        private Long latestNoticeId;
        private String latestNoticeTitle;
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
