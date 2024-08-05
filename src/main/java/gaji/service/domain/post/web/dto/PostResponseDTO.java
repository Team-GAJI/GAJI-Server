package gaji.service.domain.post.web.dto;

import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private List<HashtagResponseDTO.BaseResponseDTO> hashtagList = new ArrayList<>();
    }
}
