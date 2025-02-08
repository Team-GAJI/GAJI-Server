package gaji.service.domain.chat.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMessageDTO {
    private String content;
    private String imageURL;
}
