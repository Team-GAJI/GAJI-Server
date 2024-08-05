package gaji.service.domain.room.service;

import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.exception.RestApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryServiceImpl implements RoomQueryService {

    @PersistenceContext
    private EntityManager em;
    private final RoomRepository roomRepository;

    @Override
    public Room findRoomById(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));
    }


}
