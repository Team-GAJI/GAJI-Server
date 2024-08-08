package gaji.service.domain.room.repository;

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

    @Override
    public List<Room> findTop3OngoingAndTop3EndedRoomsByUser(User user, Integer limit) {
        QRoom room = QRoom.room;
        QStudyMate studyMate = QStudyMate.studyMate;

        LocalDate now = LocalDate.now();

        List<Room> ongoingRooms = jpaQueryFactory.selectFrom(room)
                .join(studyMate).on(room.id.eq(studyMate.room.id))
                .where(studyMate.user.eq(user)
                        .and(room.studyEndDay.after(now)))
                .orderBy(room.studyStartDay.desc())
                .limit(limit)
                .fetch();

        List<Room> endedRooms = jpaQueryFactory.selectFrom(room)
                .join(studyMate).on(room.id.eq(studyMate.room.id))
                .where(studyMate.user.eq(user)
                        .and(room.studyEndDay.before(now)))
                .orderBy(room.studyStartDay.desc())
                .limit(limit)
                .fetch();

        ongoingRooms.addAll(endedRooms);

        return ongoingRooms;
    }
}
