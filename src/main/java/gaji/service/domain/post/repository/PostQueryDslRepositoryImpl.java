package gaji.service.domain.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.enums.SortType;
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
    public Post findByIdFetchJoinWithUser(Long postId) {
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.user, user)
                .where(
                        postIdEq(postId)
                )
                .fetchOne();
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

    private BooleanExpression ltPopularityScore(int popularityScore) {
//        return (popularityScore != null) ? post.popularityScore.lt(popularityScore) : null;
        return null;
    }

    private OrderSpecifier orderBySortType(SortType sortTypeCond) {
        return (sortTypeCond == SortType.HOT) ?
                post.popularityScore.desc() // HOT: 인기점수(popularityScore)순
                :
                (sortTypeCond == SortType.LIKE) ?
                post.createdAt.desc() // LIKE: 좋아요순
                :
                (sortTypeCond == SortType.HIT) ?
                post.hit.desc() // HIT: 조회수순
                : post.createdAt.desc(); // null or RECENT: 최신순
    }
}
