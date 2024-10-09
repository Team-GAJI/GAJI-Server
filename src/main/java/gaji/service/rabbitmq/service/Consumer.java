package gaji.service.rabbitmq.service;

import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${rabbitmq.queue.name}")
@Slf4j
public class Consumer {
    @RabbitHandler
    public void receiveMessage(final MessageDTO message) {
        log.info("메세지 수신 성공! " + message.getSenderId() + ": " + message.getContent());
    }
}
