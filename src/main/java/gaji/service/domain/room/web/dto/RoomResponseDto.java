package gaji.service.domain.room.web.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    public static class NoticeDto {
        private Long id;
        private String authorName;
        private String title;
        private String body;
        private Integer confirmCount;
        private LocalDateTime createdAt;
        private Integer viewCount;
        private String timeSincePosted;

        // 이 생성자를 추가합니다
        public NoticeDto(Long id, String authorName, String title, String body, Integer confirmCount, LocalDateTime createdAt, Integer viewCount) {
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
}
