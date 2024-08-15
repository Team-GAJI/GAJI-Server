package gaji.service.domain.recruit.web.dto;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.RecruitPostTypeEnum;
import gaji.service.domain.enums.UserActive;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RecruitResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomDTO {
        Long roomId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class studyDetailDTO {
        // 유저 관련
        String userNickName;
        UserActive userActive;
        LocalDateTime inactiveTime;

        String name;
        String imageUrl;
        RecruitPostTypeEnum recruitPostTypeEnum;
        List<CategoryEnum> postCategoryList;
        int views;
        int likes;
        int bookmarks;

        LocalDate recruitStartTime;
        LocalDate recruitEndTime;
        LocalDate studyStartTime;
        LocalDate studyEndTime;
        List<String> materialList;
        String description;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponseDTO {
        String userImage;
        String userNickName;
        Integer commentOrder;
        int depth;
        Long commentId;
        String commentWriteDate;
        String commentBody;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentListDTO {
        int commentCount;
        boolean hasNext;
        List<CommentResponseDTO> commentList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WriteCommentDTO {
        Long commentId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewDTO {
        String imageUrl;
        RecruitPostTypeEnum recruitStatus;
        int applicant;
        String name;
        Long deadLine;
        String description;
        String createdAt;
        int recruitCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewListDTO {
        List<PreviewDTO> previewList;
        boolean hasNext;
        Long lastValue;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultPreviewDTO {
        CategoryEnum category;
        boolean hasNext;
        List<PreviewDTO> previewList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultPreviewListDTO {
        List<DefaultPreviewDTO> defaultPreviewList;
        int nextIndex;
    }
}
