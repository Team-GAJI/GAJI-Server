package gaji.service.domain.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.post.entity.QComment.comment;
import static gaji.service.domain.user.entity.QUser.user;


@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Comment> findBySliceAndPostFetchJoinWithUser(Integer lastGroupNum, Post post, Pageable pageable) {
        List<Comment> commentList = jpaQueryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.user, user)
                .fetchJoin()
                .where(
                        comment.post.eq(post),
                        gtGroupNum(lastGroupNum)
                )
                .groupBy(
                        comment.groupNum,
                        comment.id,
                        comment.body,
                        comment.depth,
                        comment.status,
                        comment.user,
                        comment.createdAt
                )
                .orderBy(
                        orderByGroupNumAndDepth()
                )
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return checkLastPage(pageable, commentList);
    }

    // no-offest 페이징 처리
    private BooleanExpression gtGroupNum(Integer lastGroupNum) {
        return (lastGroupNum != null) ? comment.groupNum.gt(lastGroupNum) : null;
    }

    private Slice<Comment> checkLastPage(Pageable pageable, List<Comment> commentList) {
        boolean hasNext = false;

        // (조회한 결과 개수 > 요청한 페이지 사이즈) 이면 뒤에 데이터가 더 존재함
        if (commentList.size() > pageable.getPageSize()) {
            hasNext = true;
            commentList.remove(pageable.getPageSize()); // limit(pageable.getPageSize() + 1) 로 1개 더 가져온 데이터를 삭제해줌.
        }

        return new SliceImpl<>(commentList, pageable, hasNext);
    }

    private OrderSpecifier[] orderByGroupNumAndDepth() {
        return new OrderSpecifier[] {
                comment.groupNum.asc(),
                comment.depth.asc()
        };
    }
}
