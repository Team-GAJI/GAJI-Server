package gaji.service.domain.recruit.repository;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.room.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StudyCommentCustomRepository {
    Slice<StudyComment> findByRoomFetchJoinWithUser(
            Integer lastCommentOrder, Room room, Pageable pageable);
}
