package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.Assignment;
import jakarta.transaction.Transactional;

public interface RoomCommandService {
    @Transactional
    Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto);

    @Transactional
    RoomNotice createNotice(Long roomId, Long userId, RoomRequestDto.RoomNoticeDto requestDto);

}
