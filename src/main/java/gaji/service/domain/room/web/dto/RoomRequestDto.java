package gaji.service.domain.room.web.dto;

import gaji.service.domain.common.annotation.CheckHashtagListElement;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.annotation.ExistPostType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

}
