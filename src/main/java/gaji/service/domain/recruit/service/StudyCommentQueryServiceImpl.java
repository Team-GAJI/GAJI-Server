package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.repository.StudyCommentRepository;
import gaji.service.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyCommentQueryServiceImpl implements StudyCommentQueryService{

    private final StudyCommentRepository studyCommentRepository;

    @Override
    public List<StudyComment> findByRoomAndDepth(Room room, int depth) {
        return studyCommentRepository.findByRoomAndDepth(room, depth);
    }

    @Override
    public int getCommentCountByRoom(Room room) {
        return studyCommentRepository.countByRoom(room);
    }
}
