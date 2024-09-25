package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommonQueryServiceImpl implements PostCommonQueryService{

    private final PostCommentRepository postCommentRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final RoomRepository roomRepository;

    @Override
    public PostComment findCommentByCommentId(Long commentId){
        return postCommentRepository.findById(commentId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public RoomBoard findRoomBoardByRoomId(Long roomId){
        return roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_TROUBLE_POST)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._ROOM_BOARD_NOT_FOUND));

    }

    public Room findRoomByRoomId(Long roomId){
        return roomRepository.findById(roomId)
                 .orElseThrow( () -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));
    }
    @Override
    public RoomBoard findRoomBoardByRoomIdAndRoomPostType(Long roomId, RoomPostType roomPostType){
        return roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, roomPostType)
                .orElseGet(() -> {
                    RoomBoard newRoomBoard = RoomBoard.builder()
                            .room(findRoomByRoomId(roomId))
                            .roomPostType(roomPostType)
                            .name(findRoomByRoomId(roomId).getName())
                            .build();
                    return roomBoardRepository.save(newRoomBoard);
                });
    }
}
