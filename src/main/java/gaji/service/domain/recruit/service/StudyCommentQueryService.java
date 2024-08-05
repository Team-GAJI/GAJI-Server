package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.room.entity.Room;

import java.util.List;

public interface StudyCommentQueryService {

    List<StudyComment> findByRoomAndDepth(Room room, int depth);

    int getCommentCountByRoom(Room room);
}


