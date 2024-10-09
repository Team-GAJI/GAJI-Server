package gaji.service.domain.chat.web.controller;

import gaji.service.domain.chat.web.dto.ChatDTO;
import gaji.service.rabbitmq.service.Producer;
import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final Producer producer;

    @PostMapping("/chat")
    public void sendMessage(@RequestBody MessageDTO message) {
        producer.sendMessage(message);
    }

    @MessageMapping("/chatroom/{id}")
    public void sendMessage(@DestinationVariable("id") Long id, ChatDTO chat) {
        simpMessageSendingOperations.convertAndSend
                ("/sub/chatroom/" + chat.getChatRoomId(), chat);
        log.info("메세지 전송 성공");
        //chatMessageService.saveMessage(chatMessageReq);
    }
}
