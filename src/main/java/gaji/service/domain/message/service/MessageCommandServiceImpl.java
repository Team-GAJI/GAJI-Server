package gaji.service.domain.message.service;

import gaji.service.domain.message.converter.MessageBodyConverter;
import gaji.service.domain.message.entity.Message;
import gaji.service.domain.message.converter.MessageConverter;
import gaji.service.domain.message.entity.MessageBody;
import gaji.service.domain.message.repository.MessageBodyRepository;
import gaji.service.domain.message.repository.MessageRepository;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageCommandServiceImpl implements MessageCommandService{

    private final UserQueryService userQueryService;
    private final MessageRepository messageRepository;
    private final MessageBodyRepository messageBodyRepository;


    @Override
    @Transactional
    public List<Message> createMessage(Long myId, Long otherId, MessageRequestDTO.CreateMessageDTO request) {

        User mine = userQueryService.findUserById(myId);
        User other = userQueryService.findUserById(otherId);

        Message sendMessage = MessageConverter.toSendMessage(mine,other);
        Message receiveMessage = MessageConverter.toReceiveMessage(mine, other);

        MessageBody messageBody = MessageBodyConverter.toMessageBody(request);

        //연관관계 매핑을 위해 setter 사용
        sendMessage.setMessageBody(messageBody);
        receiveMessage.setMessageBody(messageBody);

        messageRepository.save(sendMessage);
        messageRepository.save(receiveMessage);
        messageBodyRepository.save(messageBody);

        return Arrays.asList(sendMessage, receiveMessage);
    }
}
