package gaji.service.domain.recruit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class RecruitResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRecruitDTO {
        Long recruitId;
    }
}
