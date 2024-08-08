package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.studyMate.entity.Assignment;
import jakarta.transaction.Transactional;

public interface RoomCommandService {
    @Transactional
    Assignment createAssignment(Long roomId, Long userId, RoomRequestDto.AssignmentDto requestDto);

    void createUserAssignmentsForStudyMembers(Assignment assignment);
    @Transactional
    RoomNotice createNotice(Long roomId, Long userId, RoomRequestDto.RoomNoticeDto requestDto);

    RoomEvent setStudyPeriod(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyPeriodDto requestDto);

    RoomEvent setStudyDescription(Long roomId, Integer weeks, Long userId, RoomRequestDto.StudyDescriptionDto requestDto);

    boolean toggleNoticeConfirmation(Long noticeId, Long userId);

    void saveRoom(Room room);

    void calculateAndSaveProgress(Long roomEventId, Long userId);
}
