package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.room.entity.RoomNotice;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.entity.Assignment;
import gaji.service.domain.studyMate.entity.WeeklyUserProgress;
import gaji.service.domain.user.entity.User;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    RoomResponseDto.AssignmentProgressResponse toggleAssignmentCompletion(Long userId, Long userAssignmentId);

    WeeklyUserProgress calculateAndSaveProgress(RoomEvent roomEvent, User user);

    List<Room> findRoomsByUserId(Long userId);
    List<RoomEvent> findRoomEventByRoomAndDate(Room room, LocalDate date);
}
