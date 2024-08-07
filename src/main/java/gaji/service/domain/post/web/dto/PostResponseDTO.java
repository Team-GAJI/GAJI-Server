package gaji.service.domain.post.web.dto;

import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.PostTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    public static class PostPreviewDTO {
        private Long postId;
        private int likeCnt;
        private String thumbnailUrl;
        private String title;
        private String body;
        private String username;
        private String uploadTime;
        private int viewCnt;
        private int popularityScore;
        private List<String> hashtagList = new ArrayList<>();
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDetailDTO {
        private Long userId;
        private PostTypeEnum type;
        private LocalDate createdAt;
        private int viewCnt;
        private int commentCnt;
        private String username;
        private String title;
        private boolean isBookMarked;
        private boolean isLiked;
        private String body;
        private List<HashtagResponseDTO.HashtagNameAndIdDTO> hashtagList = new ArrayList<>();
    }
}
