package gaji.service.domain.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.common.enums.SortType;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.post.entity.QPost.post;
import static gaji.service.domain.user.entity.QUser.user;


@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllFetchJoinWithUser(PostTypeEnum postType, PostStatusEnum postStatus, SortType sortType) {
        return jpaQueryFactory.
                selectFrom(post)
                .leftJoin(post.user, user)
                .fetchJoin()
                .where(
                        postTypeEq(postType),
                        postStatusEq(postStatus)
                )
                .orderBy(orderBySortType(sortType))
                .fetch();
    }

    @Override
    public Post findByIdFetchJoinWithUserAndPostBookMarkAndPostLikes(Long postId) {
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.user, user)
                .where(
                        post.id.eq(postId)
                )
                .fetchOne();
    }

    private BooleanExpression postTypeEq(PostTypeEnum postTypeCond) {
        return (postTypeCond != null) ? post.type.eq(postTypeCond) : null;
    }

    private BooleanExpression postStatusEq(PostStatusEnum postStatusCond) {
        return (postStatusCond != null) ? post.status.eq(postStatusCond) : null;
    }

    private BooleanExpression ltPopularityScore(int popularityScore) {
//        return (popularityScore != null) ? post.popularityScore.lt(popularityScore) : null;
        return null;
    }

    private OrderSpecifier[] orderBySortType(SortType sortTypeCond) {
        return (sortTypeCond == SortType.RECENT) ?
                new OrderSpecifier[] { // RECENT일 경우에는 1순위 최신순, 2순위 인기순
                        post.createdAt.desc(),
                        post.popularityScore.desc()
                }
                : new OrderSpecifier[] { // null(기본) or HOT일 경우에는 1순위 인기순, 2순위 최신순
                        post.popularityScore.desc(),
                        post.createdAt.desc()
                };
    }
}
