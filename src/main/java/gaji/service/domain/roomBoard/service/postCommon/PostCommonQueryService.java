package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;

public interface PostCommonQueryService {
    PostComment findCommentByCommentId(Long commentId);

    RoomBoard findRoomBoardByRoomId(Long roomId);

    RoomBoard findRoomBoardByRoomIdAndRoomPostType(Long roomId, RoomPostType roomPostType);
}
