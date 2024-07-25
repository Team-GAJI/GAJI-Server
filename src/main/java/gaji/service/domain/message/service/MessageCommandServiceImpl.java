package gaji.service.domain.message.service;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.converter.MessageConverter;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MessageCommandServiceImpl implements MessageCommandService{
    public Message createMessage(Long myId,Long otherId, MessageRequestDTO.CreateDTO request) {
        Message newMessage = MessageConverter.toMessage(myId,otherId,request);
        return null;
    }
}
