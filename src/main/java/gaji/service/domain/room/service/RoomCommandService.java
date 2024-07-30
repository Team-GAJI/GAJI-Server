package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Event;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.Assignment;
import jakarta.transaction.Transactional;

import java.util.List;

public interface RoomCommandService {
    @Transactional
    Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto);

    List<Event> createEvent(Long roomId, Long userId, RoomRequestDto.EventDto requestDto);
}
