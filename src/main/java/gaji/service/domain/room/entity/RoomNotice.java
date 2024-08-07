package gaji.service.domain.room.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    private String title;

    private String body;

    private Integer viewCount;
    private Integer confirmCount;

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
        this.confirmCount = 0;
    }
}
