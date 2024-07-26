package gaji.service.domain.message.service;

import gaji.service.domain.message.Message;
import gaji.service.domain.message.web.dto.MessageRequestDTO;

public interface MessageCommandService {
    public Message createMessage(Long myId, Long otherId, MessageRequestDTO.CreateDTO request);
}
