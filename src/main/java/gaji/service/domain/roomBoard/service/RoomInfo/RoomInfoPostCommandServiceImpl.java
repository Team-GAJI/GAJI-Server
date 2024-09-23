package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPostBookmark;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPostLikes;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.InfoPostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostBookmarkRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostLikesRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostLikesRepository;
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
public class RoomInfoPostCommandServiceImpl implements RoomInfoPostCommandService {
    private final RoomInfoPostRepository roomInfoPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomInfoPostQueryService roomInfoPostQueryService;
    private final InfoPostCommentRepository infoPostCommentRepository;
    private final RoomPostLikesRepository roomPostLikesRepository;
    private final RoomInfoPostLikesRepository roomInfoPostLikesRepository;
    private final RoomInfoPostBookmarkRepository roomInfoPostBookmarkRepository;

    // TODO: 정보나눔 게시판 작성
    @Override
    public RoomPostResponseDto.toCreateRoomInfoPostIdDTO createRoomInfoPostIdDTO(Long roomId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto) {
        //user 찾기
        User user = userQueryService.findUserById(userId);

        //스터디룸 찾기
        Room room = roomQueryService.findRoomById(roomId);

        // 스터디룸에서 사용자 찾기
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성
        RoomBoard roomBoard = roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_INFORMATION_POST)
                .orElseGet(() -> {
                    RoomBoard newRoomBoard = RoomBoard.builder()
                            .room(room)
                            .roomPostType(RoomPostType.ROOM_INFORMATION_POST)
                            .name(room.getName())
                            .build();
                    return roomBoardRepository.save(newRoomBoard);
                });


        //RoomInfoPost 생성
        RoomInfoPost roomInfoPost = RoomInfoPost.builder()
                .studyMate(studyMate)
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .roomBoard(roomBoard)
                .build();

        //RoomInfoPost 저장
        roomInfoPostRepository.save(roomInfoPost);

        //RoomInfoPost 생성된 객체 id 반환
        return RoomPostConverter.infoPostIdDto(roomInfoPost.getId());
    }

    //TODO: 정보나눔게시글 수정
    @Override
    public void updateInfoPost(Long postId, Long userId, RoomPostRequestDto.RoomInfoPostDto requestDto) {
        // 게시글 찾기
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        // 게시글 작성자와 수정자가 일치하는지 검증
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        // 업데이트
        post.update(requestDto.getTitle(), requestDto.getBody());
    }

    //TODO: 정보나눔게시글 삭제
    @Override
    public void deleteInfoPost(Long postId, Long userId) {
        // 게시글 찾기
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        // 게시글 작성자와 삭제하려는 사람이 일치하는지 검증
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND);
        }

        // 게시글 삭제
        roomInfoPostRepository.delete(post);
    }

    //TODO: 정보나눔 게시글에 댓글 달기
    @Override
    public RoomPostResponseDto.toWriteCommentDto writeCommentOnInfoPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        // user 찾기
        User user = userQueryService.findUserById(userId);

        //정보나눔 게시글 찾기
        RoomInfoPost roomPost = roomInfoPostQueryService.findInfoPostById(postId);

        // 게시글 댓글 찾기
        InfoPostComment postComment = InfoPostComment.builder()
                .user(user)
                .roomInfoPost(roomPost)
                .body(request.getBody())
                .build();

        // 댓글 저장
        infoPostCommentRepository.save(postComment);

        // 저장한 댓글의 id 반환
        return RoomPostConverter.toWriteInfoPostCommentDto(postComment);
    }

    //TODO: 댓글 수정
    @Override
    public void updateComment(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto) {

        // 댓글 찾기
        InfoPostComment comment = roomInfoPostQueryService.findCommentByCommentId(commentId);

        // 댓글 작성자와 수정자가 일치하는지 검사
        if (!comment.isAuthor(userId)){
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_COMMENT_UPDATE_AUTH);
        }

        // 댓글 업데이트
        comment.updateComment(requestDto.getBody());
    }



    //TODO: 댓글 삭제
    @Override
    public void deleteComment(Long commentId, Long userId) {

        //댓글 조회
        InfoPostComment comment = roomInfoPostQueryService.findPostCommentById(commentId);

        //댓글 작성자와 삭제하려는 자가 같은 지 검증
        if (!comment.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_COMMENT_DELETE_AUTH);
        }

        if (comment.isReply()) {
            // 답글인 경우, 해당 답글만 삭제
            deleteReply(comment);
        } else {
            // 댓글인 경우, 댓글과 모든 관련 답글 삭제
            deleteCommentAndReplies(comment);
        }


    }

    //TODO: 답글 삭제
    private void deleteReply(InfoPostComment reply) {
        //부모 댓글 찾기
        InfoPostComment parentComment = reply.getParentComment();

        //댓글에 대한 답글 삭제
        parentComment.getReplies().remove(reply);

        //댓글에 대한 답글 삭제
        infoPostCommentRepository.delete(reply);
    }

    //TODO: 댓글 답글 모두 삭제
    private void deleteCommentAndReplies(InfoPostComment comment) {
        // CascadeType.ALL과 orphanRemoval = true 설정으로 인해
        // 댓글을 삭제하면 연관된 모든 답글도 자동으로 삭제됩니다.
        infoPostCommentRepository.delete(comment);
    }

    //TODO: 게시글 좋아요 설정
    @Override
    public void addLike(Long postId, Long userId, Long roomId) {

        // 게시글 조회
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        //스터디룸에 참여하고 있는 유저인지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        // 좋아요인지 아닌지 객체에 담기
        Optional<RoomInfoPostLikes> likeOptional = roomInfoPostLikesRepository
                .findByRoomInfoPostAndStudyMate(post, studyMate);


        if (likeOptional.isPresent()) {
            // 이미 좋아요면 에러 발생
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_LIKED);
        } else {

            // 좋아요가 아니라면 좋아요 객체 생성
            RoomInfoPostLikes newLike = RoomInfoPostLikes.builder()
                    .roomInfoPost(post)
                    .studyMate(studyMate)
                    .build();

            post.addLike(newLike);

            // 좋아요 저장
            roomInfoPostLikesRepository.save(newLike);
        }
    }


    //TODO: 좋아요 삭제
    @Override
    public void removeLike(Long postId, Long userId, Long roomId) {

        //게시글 삭제
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        //스터디룸에 참여하고 있는 유저인지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        // 좋아요 조회
        RoomInfoPostLikes like = roomInfoPostLikesRepository
                .findByRoomInfoPostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus. _POST_LIKE_NOT_FOUND));

        post.removeLike(like);

        //좋아요 삭제
        roomInfoPostLikesRepository.delete(like);
    }

    //TODO: 북마크 추가
    @Override
    public void addBookmark(Long postId, Long userId, Long roomId) {

        // 게시글 찾기
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        //스터디룸에 참여하고 있는 유저인지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);


        // 이미 북마크가 돼있다면 에러 발생
        if (roomInfoPostBookmarkRepository.findByRoomInfoPostAndStudyMate(post, studyMate).isPresent()) {
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_BOOKMARKED);
        }else {
            //새로운 북마크 생성
            RoomInfoPostBookmark newBookmark = RoomInfoPostBookmark.builder()
                    .roomInfoPost(post)
                    .studyMate(studyMate)
                    .build();
            post.addBookmark(newBookmark);

            //북마크 추가하기
            roomInfoPostBookmarkRepository.save(newBookmark);
        }
    }

    //TODO: 북마크 제거하기
    @Override
    public void removeBookmark(Long postId, Long userId, Long roomId) {

        // 게시글 조회
        RoomInfoPost post = roomInfoPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        //스터디룸에 참여하고 있는 유저인지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        //북마크 조회
        RoomInfoPostBookmark bookmark = roomInfoPostBookmarkRepository
                .findByRoomInfoPostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_BOOKMARKED_NOT_FOUND));

        post.removeBookmark(bookmark);
        //북마크 삭제
        roomInfoPostBookmarkRepository.delete(bookmark);
    }


    //TODO: 답글 추가
    @Override
    public InfoPostComment addReply(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto request) {

        // 댓글 찾기
        InfoPostComment parentComment = roomInfoPostQueryService.findInfoParentComment(commentId);


        // 찾은 댓글이 덧글이라면 에러 발생
        if (parentComment.isReply()) {
            throw new IllegalStateException("답글에는 답글을 달 수 없습니다.");
        }

        //user 찾기
        User user = userQueryService.findUserById(userId);

        //답글 생성
        InfoPostComment reply = InfoPostComment.builder()
                .user(user)
                .roomInfoPost(parentComment.getRoomInfoPost())
                .body(request.getBody())
                .isReply(true)
                .parentComment(parentComment)
                .build();

        parentComment.addReply(reply);
        //답글 저장
        return infoPostCommentRepository.save(reply);
    }
}

