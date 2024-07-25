package gaji.service.domain.message.web.controller;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.converter.MessageConverter;
import gaji.service.domain.message.service.MessageCommandService;
import gaji.service.domain.message.service.MessageQueryService;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import gaji.service.domain.message.web.dto.MessageResponseDTO;
import gaji.service.global.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;

    @PostMapping("/")
    public BaseResponse<MessageResponseDTO.SendResultDTO> send(@PathVariable Long otherId, @RequestBody MessageRequestDTO.SendDTO request){
        Message message = messageCommandService.sendMessage();
        return BaseResponse.onSuccess(MessageConverter.toSendResultDTO(message));
    }
}
