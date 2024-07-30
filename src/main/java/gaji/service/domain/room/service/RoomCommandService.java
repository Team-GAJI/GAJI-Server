package gaji.service.domain.room.service;

import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import jakarta.transaction.Transactional;

public interface RoomCommandService {
    @Transactional
    RoomResponseDto createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto);
}
