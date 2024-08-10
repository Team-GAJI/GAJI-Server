package gaji.service.domain.room.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.room.entity.QRoom;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.QStudyMate;
import gaji.service.domain.user.entity.User;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class RoomCustomRepositoryImpl implements RoomCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Tuple> findAllOngoingRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Integer limit) {
        QRoom room = QRoom.room;
        QStudyMate studyMate = QStudyMate.studyMate;

        LocalDate now = LocalDate.now();

        BooleanExpression cursorCondition = (room.studyStartDay.eq(cursorDate).and(room.id.gt(cursorId)))
                .or(room.studyStartDay.lt(cursorDate));

        List<Tuple> ongoingRooms = jpaQueryFactory.select(room.id, room.name, room.description, room.thumbnailUrl, room.studyStartDay)
                .from(room)
                .join(studyMate).on(room.id.eq(studyMate.room.id))
                .where(studyMate.user.eq(user).and(room.studyEndDay.after(now))
                        .and(cursorCondition))
                .orderBy(room.studyStartDay.desc(), room.id.asc())
                .limit(limit)
                .fetch();

        return ongoingRooms;
    }

    @Override
    public List<Tuple> findAllEndedRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Integer limit) {
        QRoom room = QRoom.room;
        QStudyMate studyMate = QStudyMate.studyMate;

        LocalDate now = LocalDate.now();

        BooleanExpression cursorCondition = (room.studyStartDay.eq(cursorDate).and(room.id.gt(cursorId)))
                .or(room.studyStartDay.lt(cursorDate));

        List<Tuple> endedRooms = jpaQueryFactory.select(room.id, room.name, room.description, room.thumbnailUrl)
                .from(room)
                .join(studyMate).on(room.id.eq(studyMate.room.id))
                .where(studyMate.user.eq(user)
                        .and(room.studyEndDay.before(now)).and(cursorCondition))
                .orderBy(room.studyStartDay.desc(), room.id.asc())
                .limit(limit)
                .fetch();

        return endedRooms;
    }
}
