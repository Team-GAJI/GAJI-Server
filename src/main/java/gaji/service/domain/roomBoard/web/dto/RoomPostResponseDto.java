package gaji.service.domain.roomBoard.web.dto;

import gaji.service.global.converter.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RoomPostResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomPostDTO {
        Long postId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostListDto {
        private Long postId;
        private String title;
        private String content;
        private Integer viewCount;
        private LocalDateTime postTime;
        private Long userId;
        private String profileImageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class toCreateRoomTroublePostIdDTO {
        Long troublePostId;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class toWriteCommentDto {
        Long commentId;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TroublePostSummaryDto {
        private Long id;
        private String title;
        private Long authorId;
        private String createdAt;
        private int viewCount;
        private int commentCount;

        public TroublePostSummaryDto(Long id, String title, Long authorId, LocalDateTime createdAt, int viewCount, int commentCount) {
            this.id = id;
            this.title = title;
            this.authorId = authorId;
            this.createdAt = DateConverter.convertToRelativeTimeFormat(createdAt);
            this.viewCount = viewCount;
            this.commentCount = commentCount;
        }
    }

}
