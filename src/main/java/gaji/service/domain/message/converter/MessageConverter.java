package gaji.service.domain.message.converter;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.web.dto.MessageResponseDTO;

public class MessageConverter {
    public static MessageResponseDTO.SendResultDTO toSendResultDTO(Message message){
        return MessageResponseDTO.SendResultDTO.builder()
                .build();
    }
}
