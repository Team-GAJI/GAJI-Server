package gaji.service.domain.recruit.entity;

import gaji.service.domain.enums.RecruitPostCategoryEnum;
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
    private RecruitPost recruitPost;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private RecruitPostCategoryEnum category;

    @Builder
    public SelectCategory(RecruitPost recruitPost, RecruitPostCategoryEnum category) {
        this.recruitPost = recruitPost;
        this.category = category;
    }
}
