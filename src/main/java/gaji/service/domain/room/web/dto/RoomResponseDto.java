package gaji.service.domain.room.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gaji.service.domain.studyMate.entity.Assignment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RoomResponseDto {

    @Getter
    @Setter
    @Builder
    public static class AssignmentResponseDto {
        private List<Long> assignmentIds;

        public static AssignmentResponseDto of(List<Assignment> assignments) {
            List<Long> ids = assignments.stream()
                    .map(Assignment::getId)
                    .collect(Collectors.toList());
            return AssignmentResponseDto.builder()
                    .assignmentIds(ids)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class EventResponseDto {
        private Long eventId;

        public static EventResponseDto of(Long eventId) {
            return EventResponseDto.builder()
                    .eventId(eventId)
                    .build();
        }
    }

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
        Long noticeId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    public static class RoomMainDto {
        private String name;
        private LocalDate startDay;
        private LocalDate endDay;
        private LocalDate recruitStartDay;
        private LocalDate recruitEndDay;
        private Long daysLeftForRecruit;
        private Long applicantCount;

        // 수정된 생성자
        public RoomMainDto(String name, LocalDate startDay, LocalDate endDay,
                           LocalDate recruitStartDay, LocalDate recruitEndDay,
                           Long daysLeftForRecruit, Long applicantCount) {
            this.name = name;
            this.startDay = startDay;
            this.endDay = endDay;
            this.recruitStartDay = recruitStartDay;
            this.recruitEndDay = recruitEndDay;
            this.applicantCount = applicantCount;

            if(daysLeftForRecruit < 0 ){
                this.daysLeftForRecruit = 0L;
            }else{
                this.daysLeftForRecruit = daysLeftForRecruit;
            }

        }
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

    @Getter
    @AllArgsConstructor
    public static class NoticeDtoList{
        private List<NoticeDto> noticeDtoList;
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
    @Setter
    @Builder
    public static class AssignmentProgressResponse {
        @JsonProperty("progressPercentage")
        private Double progressPercentage;

        @JsonProperty("completedAssignments")
        private Integer completedAssignments;

        @JsonProperty("totalAssignments")
        private Integer totalAssignments;

        @JsonProperty("isCompleted")
        private Boolean isCompleted;

        @JsonProperty("deadline")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate deadline;
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


    // 스터디룸 메인 게시판 글 불러오기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomMainNoticeDto {
        private Long id;
        private String authorName;
        private String title;
        private String body;
        private Long confirmCount;
        private LocalDateTime createdAt;
        private Integer viewCount;
        private String timeSincePosted;

        // 이 생성자를 추가합니다
        public RoomMainNoticeDto(Long id, String authorName, String title, String body, Long confirmCount, LocalDateTime createdAt, Integer viewCount) {
            this.id = id;
            this.authorName = authorName;
            this.title = title;
            this.body = body;
            this.confirmCount = confirmCount;
            this.createdAt = createdAt;
            this.viewCount = viewCount;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class IsConfirmedResponse{
        private Boolean isConfirmed;
    }



}
