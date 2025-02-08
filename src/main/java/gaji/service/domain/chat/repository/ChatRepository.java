package gaji.service.domain.chat.repository;

import gaji.service.domain.studyMate.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
