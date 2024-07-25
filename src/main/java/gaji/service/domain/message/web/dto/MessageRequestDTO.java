package gaji.service.domain.message.web.dto;

import lombok.Getter;

public class MessageRequestDTO {

    @Getter
    public static class SendDTO{
        private String body;
    }
}
