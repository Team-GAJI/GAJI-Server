package gaji.service.domain.recruit.web.dto;

import gaji.service.domain.enums.RoomCategoryEnum;
import gaji.service.domain.enums.RecruitPostTypeEnum;
import gaji.service.domain.enums.UserActive;
import gaji.service.domain.room.entity.Material;
import lombok.*;

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
        List<RoomCategoryEnum> postCategoryList;
        int views;
        int likes;
        int bookmarks;

        LocalDate recruitStartTime;
        LocalDate recruitEndTime;
        LocalDate studyStartTime;
        LocalDate studyEndTime;
        List<String> materialList;
        String description;
        int commentCount;
        List<CommentResponseDTO> commentList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponseDTO {
        String userImage;
        String userNickName;
        LocalDateTime commentCreatedAt;
        String commentBody;
    }
}
