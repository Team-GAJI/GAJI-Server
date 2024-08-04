package gaji.service.domain.roomPost.service;

import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomPost.code.RoomPostErrorStatus;
import gaji.service.domain.roomPost.converter.RoomPostConverter;
import gaji.service.domain.roomPost.entity.RoomBoard;
import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.repository.RoomBoardRepository;
import gaji.service.domain.roomPost.repository.RoomPostRepository;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import gaji.service.domain.studyMate.code.StudyMateErrorStatus;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.service.UserQueryServiceImpl;
import gaji.service.global.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomPostCommandServiceImpl implements RoomPostCommandService {
    private final RoomPostRepository roomPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final StudyMateRepository studyMateRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;

    @Override
    public RoomPost createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성
        RoomBoard roomBoard = roomBoardRepository.findByRoomId(roomId)
                .orElseGet(() -> {
                    RoomBoard newRoomBoard = RoomBoard.builder()
                            .room(room)
                            .name(room.getName())
                            .build();
                    return roomBoardRepository.save(newRoomBoard);
                });

        // RoomPost 생성 및 저장
        RoomPost roomPost = RoomPostConverter.toRoomPost(requestDto, user, roomBoard);
        roomPost = roomPostRepository.save(roomPost);

        // RoomBoard에 새로운 RoomPost 추가
        roomBoard.addRoomPost(roomPost);
        roomBoardRepository.save(roomBoard);

        return roomPost;
    }

}
