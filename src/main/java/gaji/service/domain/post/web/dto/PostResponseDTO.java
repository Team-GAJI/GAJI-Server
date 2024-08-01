package gaji.service.domain.post.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePostDTO {
        Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviewListDTO {
        private int likeCnt;
        private String thumbnailUrl;
        private String title;
        private String body;
        private String username;
        private String uploadTime;
        private int viewCnt;
    }
}
