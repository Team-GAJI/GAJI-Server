package gaji.service.domain.message.converter;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.enums.MessageTypeEnum;
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
                .myId()
                .otherId(otherId)
                .type(MessageTypeEnum.Send)
                .sendDate(LocalDateTime.now())
                .build();
    }

    public static Message receiveMessage(Long myId, Long otherId, MessageRequestDTO.CreateDTO messageRequestDTO){
        return Message.builder()
                .myId(otherId)
                .otherId(myId)
                .type(MessageTypeEnum.Receive)
                .sendDate(LocalDateTime.now()).
                build();
    }
}
