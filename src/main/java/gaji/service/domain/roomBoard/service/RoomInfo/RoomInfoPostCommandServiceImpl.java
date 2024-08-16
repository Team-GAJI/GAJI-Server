package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomInfoPostCommandServiceImpl implements RoomInfoPostCommandService {
    private final RoomInfoPostRepository roomInfoPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomTroublePostRepository roomTroublePostRepository;


    @Override
    public RoomPostResponseDto.toCreateRoomInfoPostIdDTO createRoomInfoPostIdDTO(Long roomId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성
        RoomBoard roomBoard = roomBoardRepository.findByRoomId(roomId)
                .orElseGet(() -> {
                    RoomBoard newRoomBoard = RoomBoard.builder()
                            .room(room)
                            .roomPostType(RoomPostType.ROOM_TROUBLE_POST)
                            .name(room.getName())
                            .build();
                    return roomBoardRepository.save(newRoomBoard);
                });


        RoomInfoPost roomInfoPost = RoomPostConverter.toRoomInfoPost(requestDto, studyMate,roomBoard);
        roomInfoPostRepository.save(roomInfoPost);

        return RoomPostConverter.infoPostIdDto(roomInfoPost.getId());
    }
}