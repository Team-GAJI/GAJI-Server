package gaji.service.domain.recruit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.annotation.ExistCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class RecruitRequestDTO {

    @Schema(description = "모집 게시글 생성 DTO")
    @Getter
    @RequiredArgsConstructor
    public static class CreateRecruitDTO {

        @Schema(description = "모집 게시글 제목")
        private String title;

        @Schema(description = "모집 게시글 내용")
        private String content;

        @Schema(description = "스터디 모집 시작일")
        @NotNull(message = "스터디 모집 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startTime;

        @Schema(description = "스터디 모집 종료일")
        @NotNull(message = "스터디 모집 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate endTime;

        // true 시 초대코드 생성
        @Schema(description = "공개 여부")
        @JsonProperty("private")
        private boolean isPrivate;

        //인원제한 여부 Ture : 제한있음 / False : 제한없음
        @Schema(description = "인원 제한 여부")
        private boolean peopleLimited;

        //인원제한 True 일 시 입력(false 면 0) -> 인원 채워지면 모집 완료로 변경
        @Schema(description = "최대 인원")
        private int peopleMaximum;

        @Schema(description = "카테고리 목록")
        // @ExistCategory
        private List<RecruitPostCategoryEnum> categoryList;
    }
}
