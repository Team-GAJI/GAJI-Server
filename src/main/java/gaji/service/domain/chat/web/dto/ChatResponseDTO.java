package gaji.service.domain.chat.web.dto;

import gaji.service.domain.enums.UserActive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnterResultDTO {
        Long chatUserId;
        Long chatRoomId;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendResultDTO {
        String chatMessageId;
    }
}
