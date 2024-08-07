package gaji.service.domain.post.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class CommentResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostCommentDTO {
        private Long commentId;
        private Long userId;
        private String username;
        private String body;
        private int orderNum;
        private int depth;
        private LocalDate createdAt;
    }
}
