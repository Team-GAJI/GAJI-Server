package gaji.service.domain.room.service;

import gaji.service.domain.room.web.dto.AssignmentRequestDto;
import gaji.service.domain.room.web.dto.AssignmentResponseDto;
import jakarta.transaction.Transactional;

public interface RoomAssignmentService {
    @Transactional
    AssignmentResponseDto createAssignment(Long roomId, AssignmentRequestDto.AssignmentDto requestDto);
}
