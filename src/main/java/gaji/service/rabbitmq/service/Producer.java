package gaji.service.rabbitmq.service;

import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    public void sendMessage(MessageDTO message) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), message.getRoutingKey(), message);
        log.info("메세지 발신 성공");
    }
}
