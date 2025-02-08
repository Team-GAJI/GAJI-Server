package gaji.service.domain.studyMate.entity;

import gaji.service.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    private String id;

    /**
     * 유저 정보가 자주 변경되거나
     * 챗메시지가 많아 질수록 참조하는 방식으로 가는 것이 유리
     **/
    private Long userId; //유저의 고유 ID를 참조하는 방식으로

    private String nickname;

    private Long chatRoomId;

    private String content;

    private LocalDateTime createdAt;

    private String imageUrl;

}
