package gaji.service.domain.room.web.dto;

import gaji.service.domain.room.validation.annotation.ValidWeek;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class AssignmentRequestDto {

    @Schema(description = "과제 등록 DTO")
    @Getter
    @RequiredArgsConstructor
    public static class AssignmentDto {

        @Schema(description = "주차")
        @NotNull(message = "주차를 입력해주세요.")
        @ValidWeek
        private final Integer week;

        @Schema(description = "과제 입력")
        @NotEmpty(message = "1개 이상의 과제를 입력해주세요.")
        private final List<String> bodyList = new ArrayList<>();

    }
}
