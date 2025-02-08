package gaji.service.domain.chat.repository;

import com.querydsl.core.Tuple;
import gaji.service.domain.studyMate.entity.ChatMessage;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    // chatRoomId로 채팅 메시지 조회
    Slice<Tuple> findByChatRoomId(Long chatRoomId);

    // chatRoomId와 생성 시간으로 필터링
    Slice<Tuple> findByChatRoomIdAndCreatedAtAfter(Long chatRoomId, LocalDateTime createdAt);
}

