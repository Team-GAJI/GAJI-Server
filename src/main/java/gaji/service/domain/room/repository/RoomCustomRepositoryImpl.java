package gaji.service.domain.room.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.room.entity.QRoom;
import gaji.service.domain.studyMate.QStudyMate;
import gaji.service.domain.user.entity.User;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class RoomCustomRepositoryImpl implements RoomCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Tuple> findAllOngoingRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Pageable pageable) {
        QRoom room = QRoom.room;
        QStudyMate studyMate = QStudyMate.studyMate;

        LocalDate now = LocalDate.now();

        BooleanExpression cursorCondition = (room.studyStartDay.eq(cursorDate).and(room.id.gt(cursorId)))
                .or(room.studyStartDay.lt(cursorDate));

        List<Long> userRoomIds = jpaQueryFactory
                .select(studyMate.room.id)
                .from(studyMate)
                .where(studyMate.user.eq(user))
                .fetch();

        List<Tuple> ongoingRooms = jpaQueryFactory.select(room.id, room.name, room.description, room.thumbnailUrl, room.studyStartDay)
                .from(room)
                .where(room.id.in(userRoomIds)
                        .and(room.studyEndDay.after(now))
                        .and(cursorCondition))
                .orderBy(room.studyStartDay.desc(), room.id.asc())
                .limit(pageable.getPageSize()+1) // size보다 1개 더 가져와서 다음 페이지 여부 확인
                .fetch();

        return checkLastPage(pageable, ongoingRooms);
    }


    public Slice<Tuple> findAllEndedRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Pageable pageable) {
        QRoom room = QRoom.room;
        QStudyMate studyMate = QStudyMate.studyMate;

        LocalDate now = LocalDate.now();

        BooleanExpression cursorCondition = (room.studyStartDay.eq(cursorDate).and(room.id.gt(cursorId)))
                .or(room.studyStartDay.lt(cursorDate));

        List<Long> userRoomIds = jpaQueryFactory
                .select(studyMate.room.id)
                .from(studyMate)
                .where(studyMate.user.eq(user))
                .fetch();

        List<Tuple> ongoingRooms = jpaQueryFactory.select(room.id, room.name, room.description, room.thumbnailUrl, room.studyStartDay)
                .from(room)
                .where(room.id.in(userRoomIds)
                        .and(room.studyEndDay.before(now))
                        .and(cursorCondition))
                .orderBy(room.studyStartDay.desc(), room.id.asc())
                .limit(pageable.getPageSize()+1) // size보다 1개 더 가져와서 다음 페이지 여부 확인
                .fetch();

        return checkLastPage(pageable, ongoingRooms);
    }


    private Slice<Tuple> checkLastPage(Pageable pageable, List<Tuple> roomList) {
        boolean hasNext = false;

        if (roomList.size() > pageable.getPageSize()) {
            hasNext = true;
            roomList.remove(pageable.getPageSize()); // 더 가져왔을 시, 삭제
        }
        return new SliceImpl<Tuple>(roomList, pageable, hasNext);
    }
}
