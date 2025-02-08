package gaji.service.domain.chat.repository;

import gaji.service.domain.studyMate.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
