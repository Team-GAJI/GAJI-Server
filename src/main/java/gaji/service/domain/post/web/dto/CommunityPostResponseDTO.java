package gaji.service.domain.post.web.dto;

import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class CommunityPostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadPostDTO {
        Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostBookmarkIdDTO {
        Long postBookmarkId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostLikesIdDTO {
        Long postLikesId;
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
        private Long userId;
        private String userNickname;
        private String uploadTime;
        private int hit;
        private int popularityScore;
        private List<String> hashtagList = new ArrayList<>();
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviewListDTO {
        private List<PostPreviewDTO> postList = new ArrayList<>();
        private boolean hasNext;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDetailDTO {
        private CategoryEnum category;
        private Long userId;
        private PostTypeEnum type;
        private String createdAt;
        private int hit;
        private int commentCnt;
        private String userNickname;
        private String title;
        private boolean isBookMarked;
        private boolean isLiked;
        private boolean isWriter; // 게시글을 조회한 사람이 작성자가 맞는지
        private String body;
        private List<HashtagResponseDTO.HashtagNameAndIdDTO> hashtagList = new ArrayList<>();
    }


}
