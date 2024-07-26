package gaji.service.domain.message.service;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.converter.MessageConverter;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageCommandServiceImpl implements MessageCommandService{

    UserRepository userRepository;

    @Override
    public Message createMessage(Long myId,Long otherId, MessageRequestDTO.CreateDTO request) {
        Message sendMessage = MessageConverter.sendMessage(myId,otherId,request);
        Message receiveMessage = MessageConverter.receiveMessage(myId,otherId,request);


        return null;
    }
}
