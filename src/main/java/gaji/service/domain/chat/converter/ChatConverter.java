package gaji.service.domain.chat.converter;

import gaji.service.domain.chat.web.dto.ChatResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.entity.Chat;
import gaji.service.domain.studyMate.entity.ChatMessage;
import gaji.service.domain.studyMate.entity.ChatUser;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class ChatConverter {

    public static Chat toChatRoom(Room room){
        return Chat.builder()
                .room(room)
                .build();
    }

    public static ChatUser toChatUser(User user, Chat chatRoom){
        return ChatUser.builder()
                .user(user)
                .chat(chatRoom)
                .build();
    }

    public static ChatMessage toChatMessage(User user, Long chatRoomId, String content){
        return ChatMessage.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .chatRoomId(chatRoomId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ChatResponseDTO.SendResultDTO toSendResultDTO(ChatMessage chatMessage) {
        return ChatResponseDTO.SendResultDTO.builder()
                .chatMessageId(chatMessage.getId())
                .build();
    }

    public static ChatResponseDTO.EnterResultDTO toEnterResultDTO(ChatUser chatUser) {
        return ChatResponseDTO.EnterResultDTO.builder()
                .chatUserId(chatUser.getId())
                .build();
    }
}
