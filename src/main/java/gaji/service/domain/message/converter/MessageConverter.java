package gaji.service.domain.message.converter;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import gaji.service.domain.message.web.dto.MessageResponseDTO;

import java.time.LocalDateTime;

public class MessageConverter {
    public static MessageResponseDTO.CreateResultDTO toCreateResultDTO(Message message){
        return MessageResponseDTO.CreateResultDTO.builder()
                .build();
    }

    public static Message sendMessage(Long myId, Long otherId, MessageRequestDTO.CreateDTO messageRequestDTO){

        return Message.builder()
                .sender(myId)
                .receiver(otherId)
                .sendDate(LocalDateTime.now())
    }

    public static Message receiveMessage(Long myId, Long otherId, MessageRequestDTO.CreateDTO messageRequestDTO){
        return Message.builder()
                .sender(otherId)
                .receiver(myId)
                .sendDate(LocalDateTime.now())
    }
}
