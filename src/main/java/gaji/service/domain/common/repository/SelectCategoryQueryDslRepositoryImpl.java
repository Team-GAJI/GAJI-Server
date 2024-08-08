package gaji.service.domain.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.PostTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.common.entity.QSelectCategory.selectCategory;
import static gaji.service.domain.common.entity.QCategory.category1;


@Repository
@RequiredArgsConstructor
public class SelectCategoryQueryDslRepositoryImpl implements SelectCategoryQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SelectCategory> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType) {
        return jpaQueryFactory
                .selectFrom(selectCategory)
                .join(selectCategory.category, category1)
                .fetchJoin()
                .where(
                        selectCategory.entityId.eq(entityId),
                        selectCategory.type.eq(postType)
                )
                .orderBy(selectCategory.id.asc())
                .fetch();
    }
}
