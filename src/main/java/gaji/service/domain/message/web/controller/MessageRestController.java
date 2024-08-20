package gaji.service.domain.message.web.controller;

import gaji.service.domain.message.entity.Message;
import gaji.service.domain.message.converter.MessageConverter;
import gaji.service.domain.message.service.MessageCommandService;
import gaji.service.domain.message.service.MessageQueryService;
import gaji.service.domain.message.web.dto.MessageRequestDTO;
import gaji.service.domain.message.web.dto.MessageResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;
    private final TokenProviderService tokenProviderService;

    @PostMapping("/{otherId}")
    public BaseResponse<MessageResponseDTO.CreateResultDTO> create(@RequestHeader("Authorization") String authorizationHeader,
                                                                   @PathVariable Long otherId,
                                                                   @RequestBody @Valid MessageRequestDTO.CreateMessageDTO request) {
        Long myId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        List<Message> messageList = messageCommandService.createMessage(myId, otherId, request);
        return BaseResponse.onSuccess(MessageConverter.toCreateResultDTO(messageList));
    }
}
