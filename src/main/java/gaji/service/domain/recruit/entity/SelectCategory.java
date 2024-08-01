package gaji.service.domain.recruit.entity;

import gaji.service.domain.enums.RecruitPostCategoryEnum;
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
    @JoinColumn(name = "post_id")
    private Room recruitPost;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private RecruitPostCategoryEnum category;

    @Builder
    public SelectCategory(Room recruitPost, RecruitPostCategoryEnum category) {
        this.recruitPost = recruitPost;
        this.category = category;
    }
}
