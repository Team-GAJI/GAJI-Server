package gaji.service.domain.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.post.entity.QComment.comment;
import static gaji.service.domain.user.entity.QUser.user;


@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findAllByPostFetchJoinWithUser(Post post) {
        return jpaQueryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.user, user)
                .fetchJoin()
                .where(
                        comment.post.eq(post)
                )
                .groupBy(
                        comment.orderNum,
                        comment.id,
                        comment.body,
                        comment.depth,
                        comment.status,
                        comment.user,
                        comment.createdAt
                )
                .orderBy(
                        orderByOrderNumAndDepth()
                )
                .fetch()
                ;
    }

    private OrderSpecifier[] orderByOrderNumAndDepth() {
        return new OrderSpecifier[] {
                comment.orderNum.asc(),
                comment.depth.asc()
        };
    }
}
