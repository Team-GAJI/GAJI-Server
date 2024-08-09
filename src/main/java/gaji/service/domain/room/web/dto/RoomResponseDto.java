package gaji.service.domain.room.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private Integer daysLeftForRecruit;
        private Integer applicantCount;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeDto {
        private Long id;
        private String authorName;
        private String title;
        private String body;
        private Long confirmCount;
        private LocalDateTime createdAt;
        private Integer viewCount;
        private String timeSincePosted;

        // 이 생성자를 추가합니다
        public NoticeDto(Long id, String authorName, String title, String body, Long confirmCount, LocalDateTime createdAt, Integer viewCount) {
            this.id = id;
            this.authorName = authorName;
            this.title = title;
            this.body = body;
            this.confirmCount = confirmCount;
            this.createdAt = createdAt;
            this.viewCount = viewCount;
        }

        public void setTimeSincePosted(String timeSincePosted) {
            this.timeSincePosted = timeSincePosted;
        }

        public void setViewCount(Integer viewCount) {
            this.viewCount = viewCount;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignmentProgressResponse {
        private Double progressPercentage;
        private Integer completedAssignments;
        private Integer totalAssignments;
        private Boolean isCompleted;
        private LocalDate deadline;

        // Custom method to check if the deadline has passed
        public boolean isDeadlinePassed() {
            return LocalDate.now().isAfter(deadline);
        }

        // Custom method to get remaining days until deadline
        public long getRemainingDays() {
            return LocalDate.now().until(deadline).getDays();
        }

        // Custom method to get a formatted string of progress
        public String getFormattedProgress() {
            return String.format("%.1f%%", progressPercentage);
        }
    }

    @Getter
    @Builder
    public static class UserProgressDTO {
        private String name;
        private Double progressPercentage;
    }

    @Getter
    @Builder
    public static class WeeklyStudyInfoDTO {
        private Integer weekNumber;
        private StudyPeriodDTO studyPeriod;
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class StudyPeriodDTO {
        private LocalDate startDate;
        private LocalDate endDate;
    }

}
