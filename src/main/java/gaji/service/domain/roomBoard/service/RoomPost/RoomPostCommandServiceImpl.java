package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
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
public class RoomPostCommandServiceImpl implements RoomPostCommandService {
    private final RoomPostRepository roomPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final StudyMateRepository studyMateRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final PostCommentRepository postCommentRepository;
    private final RoomPostQueryService roomPostQueryService;

    @Override
    public RoomPostResponseDto.toCreateRoomPostIdDTO createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성
        RoomBoard roomBoard = roomBoardRepository.findByRoomId(roomId)
                .orElseGet(() -> {
                    RoomBoard newRoomBoard = RoomBoard.builder()
                            .room(room)
                            .name(room.getName())
                            .roomPostType(RoomPostType.ROOM_POST)
                            .build();
                    return roomBoardRepository.save(newRoomBoard);
                });

        // RoomPost 생성 및 저장
        RoomPost roomPost = RoomPostConverter.toRoomPost(requestDto, studyMate, roomBoard);
        roomPostRepository.save(roomPost);

        return RoomPostConverter.postIdDto(roomPost.getId());
    }

    @Override
    public void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        post.update(requestDto.getTitle(), requestDto.getBody());
    }


    @Override
    public void deletePost(Long postId, Long userId) {
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND);
        }
        roomPostRepository.delete(post);
    }


    @Override
    public RoomPostResponseDto.toWriteCommentDto writeCommentOnPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        User user = userQueryService.findUserById(userId);
        RoomPost roomPost = roomPostQueryService.findPostById(postId);

        PostComment postComment = PostComment.builder()
                .user(user)
                .roomPost(roomPost)
                .body(request.getBody())
                .build();
        postCommentRepository.save(postComment);

        return RoomPostConverter.toWritePostCommentDto(postComment);
    }

}
