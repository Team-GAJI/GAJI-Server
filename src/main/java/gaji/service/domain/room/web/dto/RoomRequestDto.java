package gaji.service.domain.room.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RoomRequestDto {

    @Schema(description = "과제 등록 DTO")
    @Getter
    @RequiredArgsConstructor
    public static class AssignmentDto {

        @Schema(description = "주차")
        @NotNull(message = "주차를 입력해주세요.")
        @Min(value = 1, message = "유효하지 않은 형식의 주차입니다.")
        private Integer week;

        @Schema(description = "과제 입력")
        @NotEmpty(message = "1개 이상의 과제를 입력해주세요.")
        private List<String> bodyList = new ArrayList<>();

    }

    @Getter
    @Builder
    public static class StudyPeriodDto {
        @NotNull
        private LocalDate startDate;

        @NotNull
        private LocalDate endDate;
    }

    @Getter
    @Builder
    public static class StudyDescriptionDto {
        @NotBlank
        @Size(max = 30)
        private String title;

        @NotBlank
        @Size(max = 200)
        private String description;
    }


}
