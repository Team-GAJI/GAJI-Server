package gaji.service.rabbitmq.service;

import gaji.service.domain.chat.web.dto.ChatDTO;
import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@RequiredArgsConstructor
@Slf4j
@Controller
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(MessageDTO messageDto, @DestinationVariable String chatRoomId) {
        messageDto.setContent("입장하셨습니다.");
        // exchange
        //rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, messageDto);
        // template.convertAndSend("room." + chatRoomId, chat); //queue
        rabbitTemplate.convertAndSend("amq.topic", "room." + chatRoomId, messageDto); //topic
    }


    @MessageMapping("chat.message.{chatRoomId}")
    public void send(MessageDTO messageDto, @DestinationVariable String chatRoomId) {

        //rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);
        //template.convertAndSend( "room." + chatRoomId, chat);
        rabbitTemplate.convertAndSend("amq.topic", "room." + chatRoomId, messageDto);
    }
}
