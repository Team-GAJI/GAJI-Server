package gaji.service.domain.chat.service;

import gaji.service.domain.chat.web.dto.ChatMessageDTO;
import gaji.service.domain.studyMate.entity.ChatMessage;
import gaji.service.domain.studyMate.entity.ChatUser;
import gaji.service.domain.user.entity.User;
import gaji.service.rabbitmq.web.dto.MessageDTO;

public interface ChatCommandService {
    ChatUser registerUserToChatRoom(Long userId, Long chatRoomId);
    ChatMessage saveChatMessage(Long userId, Long roomId, ChatMessageDTO chatMessageDTO);
}
