package gaji.service.rabbitmq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String content;
    private Long senderId;
    private String routingKey;
}
