package gaji.service.domain.recruit.entity;

import gaji.service.domain.enums.RoomCategoryEnum;
import gaji.service.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private RoomCategoryEnum category;

    @Builder
    public SelectCategory(Room room, RoomCategoryEnum category) {
        this.room = room;
        this.category = category;
    }
}
