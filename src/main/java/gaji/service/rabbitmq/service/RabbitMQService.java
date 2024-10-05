package gaji.service.rabbitmq.service;

import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQService {
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    /**
     * 1. Queue 로 메세지를 발행
     * 2. Producer 역할 -> Direct Exchange 전략
     **/
    public void sendMessage(MessageDTO messageDto) {
        log.info("message sent: {}", messageDto.getContent());
        this.rabbitTemplate.convertAndSend(exchangeName,routingKey, messageDto);
    }

//    /**
//     * 1. Queue 에서 메세지를 구독
//     **/
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void receiveMessage(MessageDTO messageDto) {
//        log.info("Received Message : {}", messageDto.toString());
//    }
}
