package gaji.service.domain.recruit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.enums.RecruitPostTypeEnum;
import gaji.service.domain.enums.UserActive;
import gaji.service.domain.room.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RecruitResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRecruitDTO {
        Long recruitId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class studyDetailDTO {
        // 유저 관련
        String userNickName;
        String userClass;
        UserActive userActive;
        LocalDateTime inactiveTime;

        // 모집 게시글 관련
        int views;
        int likes;
        int bookmarks;
        RecruitPostTypeEnum recruitPostTypeEnum;
        List<RecruitPostCategoryEnum> postCategoryList;

        // 스터디 관련
        String studyName;
        String studyDescription;
        String studyImageUrl;
        LocalDate recruitStartTime;
        LocalDate recruitEndTime;
        LocalDate studyStartTime;
        LocalDate studyEndTime;
        List<Material> materialList;
    }
}
