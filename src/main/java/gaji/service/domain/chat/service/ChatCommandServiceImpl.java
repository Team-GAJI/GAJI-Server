package gaji.service.domain.chat.service;

import gaji.service.domain.chat.converter.ChatConverter;
import gaji.service.domain.chat.repository.ChatMessageRepository;
import gaji.service.domain.chat.repository.ChatRepository;
import gaji.service.domain.chat.repository.ChatUserRepository;
import gaji.service.domain.chat.web.dto.ChatMessageDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.studyMate.entity.Chat;
import gaji.service.domain.studyMate.entity.ChatMessage;
import gaji.service.domain.studyMate.entity.ChatUser;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatUserRepository chatUserRepository;
    private final ChatQueryService chatQueryService;
    private final UserQueryService userQueryService;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;
    private final RoomQueryService roomQueryService;

    public Chat createChatRoom(Long roomId){
        Room room = roomQueryService.findRoomById(roomId);

        Chat newChatRoom = chatRepository.save(ChatConverter.toChatRoom(room));
        return newChatRoom;
    }

    public ChatUser registerUserToChatRoom(Long userId, Long roomId) {
        User findUser = userQueryService.findUserById(userId);
        Chat chatRoom = chatQueryService.findChatRoomByRoomId(roomId);

        ChatUser newChatUser = chatUserRepository.save(ChatConverter.toChatUser(findUser, chatRoom));
        return newChatUser;
    }

    public ChatMessage saveChatMessage(Long userId, Long chatRoomId, ChatMessageDTO chatMessageDTO) {
        User user = userQueryService.findUserById(userId);
        ChatMessage newChatMessage = chatMessageRepository.save(ChatConverter.toChatMessage(user, chatRoomId, chatMessageDTO.getContent()));

        return newChatMessage;
    }
}
