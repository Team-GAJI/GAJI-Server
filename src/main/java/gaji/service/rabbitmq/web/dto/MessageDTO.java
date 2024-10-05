package gaji.service.rabbitmq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String content;
    private String sender;
}
