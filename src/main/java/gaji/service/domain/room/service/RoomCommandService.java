package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Event;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;
import jakarta.transaction.Transactional;

public interface RoomCommandService {
    @Transactional
    Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto);
    Event setStudyPeriod(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyPeriodDto requestDto);
    Event setStudyDescription(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyDescriptionDto requestDto);
}
