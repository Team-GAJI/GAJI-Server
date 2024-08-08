package gaji.service.domain.recruit.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.enums.*;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.room.entity.QRoom.room;
import static gaji.service.domain.common.entity.QSelectCategory.selectCategory;

@RequiredArgsConstructor
@Repository
public class RecruitCustomRepositoryImpl implements RecruitCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public RecruitResponseDTO.PreviewListDTO findByCategoryOrderBySortType(
            CategoryEnum category, PreviewFilter filter, SortType sortType, Long value, Pageable pageable) {
        List<Long> roomIds = queryFactory
                .select(selectCategory.entityId)
                .from(selectCategory)
                .where(selectCategory.type.eq(PostTypeEnum.ROOM)
                        .and(categoryEq(category))
                        .and(checkFilter(filter)))
                .fetch();

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType);

        List<Room> results = queryFactory
                .selectFrom(room)
                .where(room.id.in(roomIds)
                        .and(lastStudyValue(sortType ,value)))
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize())
                .fetch();

        List<RecruitResponseDTO.PreviewDTO> previewList = RecruitConverter.toPreviewDTOLIST(results);

        return RecruitResponseDTO.PreviewListDTO.builder()
                .previewList(previewList)
                .lastValue(getLastValue(results, sortType))
                .build();
    }

    private BooleanExpression categoryEq(CategoryEnum category) {
        return category != null ? selectCategory.category.eq(category) : null;
    }

    private BooleanExpression checkFilter(PreviewFilter filter) {
        return switch (filter) {
            case RECRUITING -> room.recruitPostTypeEnum.eq(RecruitPostTypeEnum.RECRUITING);
            case RECRUITMENT_COMPLETED -> room.recruitPostTypeEnum.eq(RecruitPostTypeEnum.RECRUITMENT_COMPLETED);
            case PEOPLE_LIMITED -> room.peopleLimited.eq(true);
            case PEOPLE_NOT_LIMITED -> room.peopleLimited.eq(false);
            default -> throw new RestApiException(RecruitErrorStatus._INVALID_FILTER);
        };
    }

    private BooleanExpression lastStudyValue(SortType sortType, Long value) {
        if (value == null || value == 0) {
            return null;
        }
        return switch (sortType) {
            case RECENT -> room.id.lt(value);
            case LIKE -> room.likes.lt(value);
            case VIEW -> room.views.lt(value);
            default -> throw new RestApiException(RecruitErrorStatus._INVALID_SORT_TYPE);
        };
    }

    private OrderSpecifier<?> getOrderSpecifier(SortType sortType) {
        if (sortType == null) {
            return null;
        }
        return switch (sortType) {
            case RECENT -> room.id.desc();
            case LIKE -> room.likes.desc();
            case VIEW -> room.views.desc();
            default -> throw new RestApiException(RecruitErrorStatus._INVALID_SORT_TYPE);
        };
    }

    private Long getLastValue(List<Room> results, SortType sortType) {
        if (results.isEmpty()) {
            return null;
        }
        return switch (sortType) {
            case RECENT -> results.get(results.size() - 1).getId();
            case LIKE -> (long) results.get(results.size() - 1).getLikes();
            case VIEW -> (long) results.get(results.size() - 1).getViews();
            default -> throw new RestApiException(RecruitErrorStatus._INVALID_SORT_TYPE);
        };
    }
}

