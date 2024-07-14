package gaji.service.domain.roomPost;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private StudyMate studyMate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomBoard_id")
    private RoomBoard roomBoard;

    private String title;
    private String body;



}
