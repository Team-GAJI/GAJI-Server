package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPostBookmark;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPostLikes;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostBookmarkRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostLikesRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostRepository;
import gaji.service.domain.roomBoard.service.postCommon.PostCommonCommandService;
import gaji.service.domain.roomBoard.service.postCommon.PostCommonQueryService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
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
public class RoomPostCommandServiceImpl implements RoomPostCommandService {
    private final RoomPostRepository roomPostRepository;
    private final RoomBoardRepository roomBoardRepository;
    private final StudyMateRepository studyMateRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final PostCommentRepository postCommentRepository;
    private final RoomPostQueryService roomPostQueryService;
    private final RoomPostLikesRepository roomPostLikesRepository;
    private final RoomPostBookmarkRepository roomPostBookmarkRepository;
    private final PostCommonCommandService postCommonCommandService;
    private final PostCommonQueryService postCommonQueryService;

    // TODO: 게시글 생성
    @Override
    public RoomPost createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성(중복여부 판단)
        RoomBoard roomBoard = roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId,RoomPostType.ROOM_POST)
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
        saveRoomPost(roomPost);

        return roomPost;
    }

    // TODO: 게시글 수정
    @Override
    public void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {

        // roomPost 조회 및 에러처리
        RoomPost post = roomPostQueryService.findRoomPostById(postId);

        // 작성자와 수정자가 일치하는지 검사
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        //업데이트
        post.update(requestDto.getTitle(), requestDto.getBody());
    }


    // TODO: 게시글 삭제
    @Override
    public void deletePost(Long postId, Long userId) {
        // roomPost 조회
        RoomPost post = roomPostQueryService.findRoomPostById(postId);

        // 작성자와 삭제하려는 사람이 일치하는지 검사
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND);
        }

        // 삭제
        roomPostRepository.delete(post);
    }


    // TODO: 게시글에 댓글 달기
    @Override
    public PostComment writeCommentOnPost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        //user 조회
        User user = userQueryService.findUserById(userId);

        //RoomPost 조회
        RoomPost roomPost = roomPostQueryService.findPostById(postId);

        // dto를 엔터티로 변환
        PostComment postComment = PostComment.builder()
                .user(user)
                .roomPost(roomPost)
                .body(request.getBody())
                .build();

        // 댓글 저장
        postCommonCommandService.savePostComment(postComment);

        return postComment;
    }

    // TODO: 댓글 업데이트
    @Override
    public void updateComment(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto) {
        //댓글 조회
        PostComment comment = postCommonQueryService.findCommentByCommentId(commentId);

        // 작성자와 수정자가 같은지 검증
        if (!comment.isAuthor(userId)){
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_COMMENT_UPDATE_AUTH);
        }

        // 댓글 업데이트
        comment.updateComment(requestDto.getBody());
    }

    // TODO : 댓글 삭제 기능
    @Override
    public void deleteComment(Long commentId, Long userId) {

        //댓글 조회
        PostComment comment = postCommonQueryService.findCommentByCommentId(commentId);

        // 작성자와 삭제하려는 사람이 같은지 검증
        if (!comment.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_COMMENT_DELETE_AUTH);
        }

        if (comment.isReply()) {
            // 답글인 경우, 해당 답글만 삭제
            postCommonCommandService.deleteReply(comment);
        } else {
            // 댓글인 경우, 댓글과 모든 관련 답글 삭제
            postCommonCommandService.deleteCommentAndReplies(comment);
        }

    }


    // TODO: 게시글 좋아요
    @Override
    public void addLike(Long postId, Long userId, Long roomId) {
        // 게시글 찾기
        RoomPost post = roomPostQueryService.findPostById(postId);

        // user 가 해당 스터디에 참여하고 있는지 검사
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        // 해당 게시글 좋아요 조회
        Optional<RoomPostLikes> likeOptional = findLikesByUserIdAndRoomId(post, studyMate);

        // 이미 좋아요면 에러 ㅂ라생
        if (likeOptional.isPresent()) {
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_LIKED);
        }

        // 새로운 좋아요 생성
        RoomPostLikes newLike = RoomPostLikes.builder()
                .roomPost(post)
                .studyMate(studyMate)
                .build();

        // 게시글에 좋아요 추가
        post.addLike(newLike);

        //좋아요 리스트 저장
        saveRoomPostLikes(newLike);

    }

    @Override
    public void removeLike(Long postId, Long userId, Long roomId) {
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        RoomPostLikes like = roomPostLikesRepository
                .findByRoomPostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus. _POST_LIKE_NOT_FOUND));

        post.removeLike(like);
        roomPostLikesRepository.delete(like);
    }

    @Override
    public void addBookmark(Long postId, Long userId, Long roomId) {
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        if (roomPostBookmarkRepository.findByRoomPostAndStudyMate(post, studyMate).isPresent()) {
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_BOOKMARKED);
        }

        RoomPostBookmark newBookmark = RoomPostBookmark.builder()
                .roomPost(post)
                .studyMate(studyMate)
                .build();
        post.addBookmark(newBookmark);
        roomPostBookmarkRepository.save(newBookmark);

    }

    @Override
    public void removeBookmark(Long postId, Long userId, Long roomId) {
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        RoomPostBookmark bookmark = roomPostBookmarkRepository
                .findByRoomPostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_BOOKMARKED_NOT_FOUND));

        post.removeBookmark(bookmark);
        roomPostBookmarkRepository.delete(bookmark);
    }

    @Override
    public PostComment addReply(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        PostComment parentComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));

        if (parentComment.isReply()) {
            throw new IllegalStateException("답글에는 답글을 달 수 없습니다.");
        }

        User user = userQueryService.findUserById(userId);
        PostComment reply = PostComment.builder()
                .user(user)
                .roomPost(parentComment.getRoomPost())
                .body(request.getBody())
                .isReply(true)
                .parentComment(parentComment)
                .build();

        parentComment.addReply(reply);
        return postCommentRepository.save(reply);
    }















    // TODO: 게시글 저장
    @Override
    public void saveRoomPost(RoomPost roomPost){
        roomPostRepository.save(roomPost);
    }
    // TODO: 좋아요 리스트 조회
    @Override
    public Optional<RoomPostLikes> findLikesByUserIdAndRoomId(RoomPost post, StudyMate studyMate){
        return roomPostLikesRepository.findByRoomPostAndStudyMate(post, studyMate);
    }

    // TODO: 게시글 좋아요 저장
    public void saveRoomPostLikes(RoomPostLikes newLike){
        roomPostLikesRepository.save(newLike);
    }

}
