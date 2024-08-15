package gaji.service.domain.recruit.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gaji.service.domain.recruit.entity.QStudyComment.studyComment;
import static gaji.service.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class StudyCommentCustomRepositoryImpl implements StudyCommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<StudyComment> findByRoomFetchJoinWithUser(
            Integer lastCommentOrder, Room room, Pageable pageable) {
        List<StudyComment> commentList = jpaQueryFactory
                .select(studyComment)
                .from(studyComment)
                .leftJoin(studyComment.user, user)
                .fetchJoin()
                .where(
                        studyComment.room.eq(room),
                        gtCommentOrder(lastCommentOrder)
                )
                .orderBy(
                        orderByCommentOrderAndDepth()
                )
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return checkLastPage(pageable, commentList);
    }

    private BooleanExpression gtCommentOrder(Integer lastCommentOrder) {
        return (lastCommentOrder != null) ? studyComment.commentOrder.gt(lastCommentOrder) : null;
    }

    private Slice<StudyComment> checkLastPage(Pageable pageable, List<StudyComment> commentList) {
        boolean hasNext = false;

        if (commentList.size() > pageable.getPageSize()) {
            hasNext = true;
            commentList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(commentList, pageable, hasNext);
    }

    private OrderSpecifier[] orderByCommentOrderAndDepth() {
        return new OrderSpecifier[] {
                studyComment.commentOrder.asc(),
                studyComment.depth.asc(),
                studyComment.createdAt.asc()
        };
    }
}
