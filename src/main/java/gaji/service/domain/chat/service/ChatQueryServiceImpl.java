package gaji.service.domain.chat.service;

import gaji.service.domain.chat.ChatErrorStatus;
import gaji.service.domain.chat.repository.ChatRepository;
import gaji.service.domain.studyMate.entity.Chat;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatQueryServiceImpl implements ChatQueryService {
    ChatRepository chatRepository;

    @Override
    public Chat findChatRoomByRoomId(Long roomId) {
        return chatRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(ChatErrorStatus._CHATROOM_NOT_FOUND_));
    }
}
