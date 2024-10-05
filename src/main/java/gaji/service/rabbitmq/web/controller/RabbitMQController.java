package gaji.service.rabbitmq.web.controller;

import gaji.service.rabbitmq.service.RabbitMQService;
import gaji.service.rabbitmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RabbitMQController {
    private final RabbitMQService rabbitMqService;

    @PostMapping("/send/message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDto) {
        this.rabbitMqService.sendMessage(messageDto);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}
