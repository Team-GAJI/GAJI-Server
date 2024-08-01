package gaji.service.domain.roomPost.web.dto;

import gaji.service.domain.common.annotation.CheckHashtagListElement;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.annotation.ExistPostType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class RoomPostRequestDto {

    @Schema(description = "스터디 게시글 DTO")
    @Getter
    @RequiredArgsConstructor
    public static class RoomPostDto {
        @Schema(description = "게시글 제목")
        @NotBlank(message = "제목을 입력해주세요.")
        private final String title;

        @Schema(description = "게시글 본문")
        @NotBlank(message = "게시글 본문을 입력해주세요.")
        private final String body;

        @Schema(description = "Enum 종류(프로젝트 모집, 질문, 블로그)")
        @ExistPostType
        private final PostTypeEnum type;

        @Schema(description = "태그 리스트")
        @CheckHashtagListElement
        private final List<String> hashtagList = new ArrayList<>();
    }



}
