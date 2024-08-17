package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.InfoPostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.global.exception.RestApiException;
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
    private final RoomInfoPostQueryService roomInfoPostQueryService;
    private final InfoPostCommentRepository infoPostCommentRepository;
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

    @Override
    public void updateInfoPost(Long postId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto) {
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        post.update(requestDto.getTitle(), requestDto.getBody());
    }


    @Override
    public void deleteInfoPost(Long postId, Long userId) {
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND);
        }
        roomInfoPostRepository.delete(post);
    }

    @Override
    public RoomPostResponseDto.toWriteCommentDto writeCommentOnInfoPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        User user = userQueryService.findUserById(userId);
        RoomInfoPost roomPost = roomInfoPostQueryService.findInfoPostById(postId);

        InfoPostComment postComment = InfoPostComment.builder()
                .user(user)
                .roomInoPost(roomPost)
                .body(request.getBody())
                .build();
         infoPostCommentRepository.save(postComment);

        return RoomPostConverter.toWriteInfoPostCommentDto(postComment);
    }

}