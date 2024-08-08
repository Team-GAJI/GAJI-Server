package gaji.service.domain.common.entity;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryEnum category;

    private Long entityId;

    @Enumerated(EnumType.STRING)
    private PostTypeEnum type;

    @Builder
    public SelectCategory(CategoryEnum category, Long entityId, PostTypeEnum type) {
        this.category = category;
        this.entityId = entityId;
        this.type = type;
    }
}
