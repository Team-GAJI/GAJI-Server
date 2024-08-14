package gaji.service.domain.roomBoard.service;

import gaji.service.domain.enums.PostLikeStatus;
import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTroublePostLike;
import gaji.service.domain.roomBoard.entity.TroublePostComment;
import gaji.service.domain.roomBoard.repository.*;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomTroublePostCommandServiceImpl implements RoomTroublePostCommandService{
    private final RoomPostRepository roomPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomTroublePostRepository roomTroublePostRepository;
    private final RoomPostCommentRepository roomPostCommentRepository;
    private final RoomTroublePostLikeRepository roomTroublePostLikeRepository;

    @Override
    public TroublePostComment writeCommentOnTroublePost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        User user = userQueryService.findUserById(userId);
        RoomTroublePost roomTroublePost = findTroublePostById(postId);

        System.out.println("트러블 슈팅: " + roomTroublePost.getBody());
        TroublePostComment troublePostComment = TroublePostComment.builder()
                .user(user)
                .roomTroublePost(roomTroublePost)
                .body(request.getBody())
                .build();
        System.out.println(troublePostComment.getBody());
        roomPostCommentRepository.save(troublePostComment);

                return troublePostComment;
    }

    @Override
    public RoomPostResponseDto.toCreateRoomTroublePostIdDTO createRoomTroublePost(Long roomId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto) {
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

//        RoomTro roomPost = RoomPostConverter.toRoomPost(requestDto, user, roomBoard);
//        roomPost = roomPostRepository.save(roomPost);


        RoomTroublePost roomTroublePost = RoomPostConverter.toRoomTroublePost(requestDto, studyMate,roomBoard);
        roomTroublePostRepository.save(roomTroublePost);

        return RoomPostConverter.troublePostIdDto(roomTroublePost.getId());
    }

    @Override
    public RoomTroublePost findTroublePostById(Long postId){
        return roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._TROUBLE_POST_NOT_FOUND));

    }

    @Override
    @Transactional
    public PostLikeStatus toggleLike(Long postId, Long userId, Long roomId) {
        RoomTroublePost post = roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId,roomId);

        Optional<RoomTroublePostLike> likeOptional = roomTroublePostLikeRepository
                .findByRoomTroublePostAndStudyMate(post, studyMate);

        if (likeOptional.isPresent()) {
            RoomTroublePostLike like = likeOptional.get();
            if (like.getStatus() == PostLikeStatus.LIKED) {
                // 이미 좋아요 상태인 경우, 좋아요 취소
                like.setStatus(PostLikeStatus.NOT_LIKED);
                post.removeLike(like);
                roomTroublePostLikeRepository.delete(like);
                return PostLikeStatus.NOT_LIKED;
            } else {
                // 좋아요가 취소된 상태인 경우, 다시 좋아요
                like.setStatus(PostLikeStatus.LIKED);
                post.addLike(like);
                roomTroublePostLikeRepository.save(like);
                return PostLikeStatus.LIKED;
            }
        } else {
            // 새로운 좋아요 생성
            RoomTroublePostLike newLike = RoomTroublePostLike.builder()
                    .roomTroublePost(post)
                    .studyMate(studyMate)
                    .status(PostLikeStatus.LIKED)
                    .build();
            post.addLike(newLike);
            roomTroublePostLikeRepository.save(newLike);
            return PostLikeStatus.LIKED;
        }
    }


    @Override
    public void deletePost(Long postId, Long userId) {
        RoomTroublePost post = roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._TROUBLE_POST_NOT_FOUND));
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._TROUBLE_POST_NOT_FOUND);
        }
        roomTroublePostRepository.delete(post);
    }

    @Override
    public void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto) {
        RoomTroublePost post = roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._TROUBLE_POST_NOT_FOUND));

        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        post.update(requestDto.getTitle(), requestDto.getBody());
    }
}
