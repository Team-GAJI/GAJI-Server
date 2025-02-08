package gaji.service.domain.chat.web.controller;

import gaji.service.domain.chat.converter.ChatConverter;
import gaji.service.domain.chat.service.ChatCommandService;
import gaji.service.domain.chat.web.dto.ChatMessageDTO;
import gaji.service.domain.chat.web.dto.ChatResponseDTO;
import gaji.service.domain.studyMate.entity.ChatMessage;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import gaji.service.rabbitmq.service.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@Controller
@Log4j2
public class ChatController {

    private final ChatCommandService chatCommandService;
    private final Producer producer;
    private final TokenProviderService tokenProviderService;

    @GetMapping("/rooms")
    public String getRooms() {
        return "rooms";
    }

    @GetMapping("/room")
    public String getRoom(Long chatRoomId, String nickname, Model model) {
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("nickname", nickname);
        return "room";
    }

    @MessageMapping("chat.send.{chatRoomId}")
    public BaseResponse<ChatResponseDTO.SendResultDTO> send(@RequestHeader("Authorization") String authorizationHeader,
                                                            @DestinationVariable Long chatRoomId,
                                                            ChatMessageDTO chatMessageDTO){

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        // 1. MongoDB에 메시지 저장
        ChatMessage chatMessage = chatCommandService.saveChatMessage(userId, chatRoomId, chatMessageDTO);

        // 2. RabbitMQ로 메시지 전송
        producer.send(chatMessageDTO, chatRoomId);

        return BaseResponse.onSuccess(ChatConverter.toSendResultDTO(chatMessage));
    }

}
