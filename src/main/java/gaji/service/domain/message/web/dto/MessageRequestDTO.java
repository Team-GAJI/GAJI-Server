package gaji.service.domain.message.web.dto;

import lombok.Getter;

public class MessageRequestDTO {

    @Getter
    public static class CreateDTO{
        private String body;
    }
}
