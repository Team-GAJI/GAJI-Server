package gaji.service.domain.message.web.dto;

import lombok.Builder;
import lombok.Getter;

public class MessageResponseDTO {
    @Builder
    @Getter
    public static class CreateResultDTO{
        Long messageId;
    }
}
