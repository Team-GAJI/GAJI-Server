package gaji.service.domain.studyMate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    // 메시지 발신인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyMate_id")
    private StudyMate studyMate;

    @Column(nullable = false)
    private String body;

}
