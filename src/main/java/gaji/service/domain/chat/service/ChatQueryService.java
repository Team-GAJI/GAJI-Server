package gaji.service.domain.chat.service;

import gaji.service.domain.studyMate.entity.Chat;
import org.springframework.stereotype.Service;

public interface ChatQueryService {
    Chat findChatRoomByRoomId(Long roomId);
}
