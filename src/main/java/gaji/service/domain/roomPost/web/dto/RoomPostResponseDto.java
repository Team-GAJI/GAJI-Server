package gaji.service.domain.roomPost.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
        private int viewCount;
        private LocalDateTime postTime;
        private Long userId;
        private String profileImageUrl;
    }

}
