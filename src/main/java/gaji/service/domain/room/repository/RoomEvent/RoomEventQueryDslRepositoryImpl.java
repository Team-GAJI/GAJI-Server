package gaji.service.domain.room.repository.RoomEvent;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.room.entity.QRoomEvent;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RoomEventQueryDslRepositoryImpl implements RoomEventQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public RoomEventQueryDslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RoomEvent> findByRoomAndDateBetweenStartTimeAndEndTime(Room room, LocalDate date) {
        QRoomEvent roomEvent = QRoomEvent.roomEvent;

        return queryFactory.selectFrom(roomEvent)
                .where(roomEvent.room.eq(room)
                        .and(roomEvent.startTime.loe(date)) // startTime <= endOfDay
                        .and(roomEvent.endTime.goe(date))) // endTime >= startOfDay
                .fetch();
    }
}
