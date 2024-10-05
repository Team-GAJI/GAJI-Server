//package gaji.service.domain.chat.web.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/chats")
//public class ChatController {
//
//    @Autowired
//    private RabbitMQProducer producer;
//
//    @GetMapping("/send")
//    public String sendMessage() {
//        String message = "Hello, RabbitMQ!";
//        producer.send(message);
//        return "Message sent!";
//    }
//}
