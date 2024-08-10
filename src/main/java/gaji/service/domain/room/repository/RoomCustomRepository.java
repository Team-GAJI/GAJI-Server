package gaji.service.domain.room.repository;

import com.querydsl.core.Tuple;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RoomCustomRepository {
    public List<Tuple> findAllOngoingRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Integer limit);
    public List<Tuple> findAllEndedRoomsByUser(User user, LocalDate cursorDate, Long cursorId, Integer limit);
}
