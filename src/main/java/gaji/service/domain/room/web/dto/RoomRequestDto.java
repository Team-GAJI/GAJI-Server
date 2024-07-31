package gaji.service.domain.room.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Schema(description = "일정 등록 DTO")
    @Getter
    @AllArgsConstructor
    public static class EventDto {
        @Schema(description = "일정 내용 입력")
        @NotBlank(message = "일정 내용을 입력해주세요.")
        private String description;

        @Schema(description = "일정 날짜 (yyyy-MM-dd)")
        private LocalDate scheduleDate;

        @Schema(description = "시작 시간 (HH:mm:ss)")
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime startTime;

        @Schema(description = "종료 시간 (HH:mm:ss)")
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime endTime;

        @Schema(description = "반복 여부")
        @JsonProperty("repeat")
        private boolean repeat;
    }

}
