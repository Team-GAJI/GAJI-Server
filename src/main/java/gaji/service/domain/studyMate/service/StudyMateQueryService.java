package gaji.service.domain.studyMate.service;

import gaji.service.domain.studyMate.entity.StudyMate;

public interface StudyMateQueryService {
    StudyMate findByUserIdAndRoomId(Long id, Long roomId);
}
