package gaji.service.domain.message.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MessageRequestDTO {

    @Getter
    @RequiredArgsConstructor
    public static class CreateDTO{
        @NotBlank(message = "쪽지 내용을 입력해주세요.")
        private final String body;
    }
}
