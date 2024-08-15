package gaji.service.domain.post.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static gaji.service.domain.post.entity.QPost.post;
import static gaji.service.domain.user.entity.QUser.user;


@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final CategoryService categoryService;

    @Override
    public Slice<Post> findAllFetchJoinWithUser(Integer lastPopularityScore,
                                                Long lastPostId,
                                                Integer lastLikeCnt,
                                                Integer lastHit,
                                                PostTypeEnum postType,
                                                PostStatusEnum postStatus,
                                                Long categoryId,
                                                SortType sortType,
                                                Pageable pageable) {
        List<Long> entityIdList = (categoryId != null) ? getEntityIdListByCategoryIdAndPostType(categoryId, postType) : null;

        List<Post> postList = jpaQueryFactory.
                selectFrom(post)
                .leftJoin(post.user, user)
                .fetchJoin()
                .where(
                        ltPopularityScore(lastPopularityScore),
                        ltPostId(lastPostId),
                        ltLikeCnt(lastLikeCnt),
                        ltHit(lastHit),
                        postTypeEq(postType),
                        postStatusEq(postStatus),
                        postIdIn(entityIdList)
                )
                .orderBy(orderBySortType(sortType))
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return checkLastPage(pageable, postList);
    }

    private List<Long> getEntityIdListByCategoryIdAndPostType(Long categoryId, PostTypeEnum postType) {
        return categoryService.findEntityIdListByCategoryIdAndPostType(categoryId, postType);
    }

    @Override
    public Post findByIdFetchJoinWithUser(Long postId) {
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.user, user)
                .where(
                        postIdEq(postId)
                )
                .fetchOne();
    }

    @Override
    public Slice<Tuple> findAllPostsByUser(User user, LocalDateTime cursorDateTime, Pageable pageable, PostTypeEnum type) {
        List<Tuple> userPosts = jpaQueryFactory.select(post.id, post.user, post.title, post.body, post.type, post.status, post.hit, post.likeCnt, post.createdAt)
                .from(post)
                .where(post.user.eq(user), (postTypeEq(type))
                        ,(post.createdAt.before(cursorDateTime))
                )
                .orderBy(post.createdAt.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return checkLastPage(pageable, userPosts);
    }

    private BooleanExpression postIdEq(Long postId) {
        return post.id.eq(postId);
    }

    private BooleanExpression postTypeEq(PostTypeEnum postTypeCond) {
        return (postTypeCond != null) ? post.type.eq(postTypeCond) : null;
    }

    private BooleanExpression postStatusEq(PostStatusEnum postStatusCond) {
        return (postStatusCond != null) ? post.status.eq(postStatusCond) : null;
    }

    private BooleanExpression categoryEq(PostStatusEnum postStatusCond) {
        return (postStatusCond != null) ? post.status.eq(postStatusCond) : null;
    }

    private BooleanExpression ltPopularityScore(Integer popularityScore) {
        return (popularityScore != null) ? post.popularityScore.lt(popularityScore) : null;
    }

    private BooleanExpression ltPostId(Long lastPostId) {
        return (lastPostId != null) ? post.id.lt(lastPostId) : null;
    }

    private BooleanExpression ltLikeCnt(Integer lastLikeCnt) {
        return (lastLikeCnt != null) ? post.likeCnt.lt(lastLikeCnt) : null;
    }

    private BooleanExpression postIdIn(List<Long> postIdList) {
        return (postIdList != null) ? post.id.in(postIdList) : null;
    }

    private BooleanExpression ltHit(Integer lastHit) {
        return (lastHit != null) ? post.hit.lt(lastHit) : null;
    }

    private <T> Slice<T> checkLastPage(Pageable pageable, List<T> postList) {
        boolean hasNext = false;

        // (조회한 결과 개수 > 요청한 페이지 사이즈) 이면 뒤에 데이터가 더 존재함
        if (postList.size() > pageable.getPageSize()) {
            hasNext = true;
            postList.remove(pageable.getPageSize()); // limit(pageable.getPageSize() + 1) 로 1개 더 가져온 데이터를 삭제해줌.
        }
        return new SliceImpl<>(postList, pageable, hasNext);
    }

    private OrderSpecifier orderBySortType(SortType sortTypeCond) {
        return (sortTypeCond == SortType.HOT) ?
                post.popularityScore.desc() // HOT: 인기점수(popularityScore) 내림차순
                :
                (sortTypeCond == SortType.LIKE) ?
                post.createdAt.desc() // LIKE: 좋아요 내림차순
                :
                (sortTypeCond == SortType.HIT) ?
                post.hit.desc() // HIT: 조회수 내림차순
                : post.createdAt.desc(); // null or RECENT: 최신순(생성일자 내림차순)
    }
}
