package gaji.service.domain.chat.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatDTO {
    private String content;
    private Long senderId;
    private Long chatRoomId;
}
