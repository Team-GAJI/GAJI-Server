package gaji.service.domain.room.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import gaji.service.domain.room.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRequestDTO {

    @Schema(description = "스터디 만들기 DTO")
    @Getter
    @RequiredArgsConstructor
    public static class CreateStudyDTO {

        @Schema(description = "커리큘럼")
        private Long curriculumId;  // 커리큘럼 아이디

        @Schema(description = "진행방식")
        private Long wayId;  // 진행방식 아이디

        @Schema(description = "스터디 명")
        @NotBlank(message = "스터디 명을 입력해주세요.")
        private String name;

        @Schema(description = "스터디 설명")
        @NotBlank(message = "스터디 설명을 입력해주세요.")
        private String description;

        @Schema(description = "스터디 인원")
        @Min(-1)
        @Max(10)
        private int headCount;

        @Schema(description = "썸네일 경로")
        private String thumbnailPath;

        @Schema(description = "스터디 자료들")
        private List<Material> materialList;

        @Schema(description = "스터디 공개 여부")
        @NotNull
        private boolean isPrivate;

        @Schema(description = "스터디 모집 기한")
        @NotNull(message = "스터디 모집 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate recruitStartDay;

        @Schema(description = "스터디 모집 기한")
        @NotNull(message = "스터디 모집 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate recruitEndDay;

        @Schema(description = "스터디 진행 기한")
        @NotNull(message = "스터디 진행 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate studyStartDay;

        @Schema(description = "스터디 진행 기한")
        @NotNull(message = "스터디 진행 기한을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate studyEndDay;

    }

}
