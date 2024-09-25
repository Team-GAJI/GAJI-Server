package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.converter.RoomTroublePostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePostBookmark;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePostLike;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostBookmarkRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostLikeRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.TroublePostCommentRepository;
import gaji.service.domain.roomBoard.service.postCommon.PostCommonQueryService;
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
    private final RoomBoardRepository roomBoardRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomTroublePostRepository roomTroublePostRepository;
    private final RoomTroublePostLikeRepository roomTroublePostLikeRepository;
    private final TroublePostCommentRepository troublePostCommentRepository;
    private final RoomTroublePostBookmarkRepository roomTroublePostBookmarkRepository;
    private final RoomTroublePostQueryService roomTroublePostQueryService;
    private final PostCommonQueryService postCommonQueryService;

    //TODO: 트러블슈팅 게시글에 댓글 작성
    @Override
    public RoomPostResponseDto.toWriteCommentDto writeCommentOnTroublePost(Long userId, Long postId, RoomPostRequestDto.RoomTroubleCommentDto request) {
        //회원 조회
        User user = userQueryService.findUserById(userId);

        //게시글 조회
        RoomTroublePost roomTroublePost = roomTroublePostQueryService.findTroublePostById(postId);

        //댓글 생성
        TroublePostComment troublePostComment = TroublePostComment.builder()
                .user(user)
                .roomTroublePost(roomTroublePost)
                .body(request.getBody())
                .build();

        //댓글 저장
        troublePostCommentRepository.save(troublePostComment);

        //게시글 id를 DTO 형식으로 반환
        return RoomPostConverter.toWriteCommentDto(troublePostComment);
    }

    //TODO: 트러블 슈팅 게시글 작성
    @Override
    public RoomPostResponseDto.toCreateRoomTroublePostIdDTO createRoomTroublePost(Long roomId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto) {
        //회원 조회
        User user = userQueryService.findUserById(userId);

        //스터디룸 조회
        Room room = roomQueryService.findRoomById(roomId);

        //스터디룸에 회원이 참여하고 있는지 검증 및 조회
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(user.getId(), roomId);

        // 스터디룸 게시판 확인 또는 생성
        RoomBoard roomBoard = postCommonQueryService.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_TROUBLE_POST);

        //객체로 변환
        RoomTroublePost roomTroublePost = RoomPostConverter.toRoomTroublePost(requestDto, studyMate,roomBoard);

        //게시글 저장
        roomTroublePostRepository.save(roomTroublePost);

        //게시글 id 반환
        return RoomPostConverter.troublePostIdDto(roomTroublePost.getId());
    }



    //TODO: 게시글 좋아요 누르기
    @Override
    public void addLike(Long postId, Long userId, Long roomId) {

        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //스터디룸에 참여하고 있는 회원 찾기
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        //좋아요가 눌러져 있는 상태인지 아닌지 조회
        Optional<RoomTroublePostLike> likeOptional = roomTroublePostLikeRepository
                .findByRoomTroublePostAndStudyMate(post, studyMate);

        //좋아요 가 눌러진 상태라면 에러 발생
        if (likeOptional.isPresent()) {
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_LIKED);
        }else{  // 좋아요가 눌러지지 않은 상태라면
            //새로운 좋아요 생성
            RoomTroublePostLike newLike = RoomTroublePostLike.builder()
                .roomTroublePost(post)
                .studyMate(studyMate)
                .build();

        post.addLike(newLike);
        //좋아요 생성
        roomTroublePostLikeRepository.save(newLike);
        }
    }


    //TODO: 게시글 좋아요 취소
    @Override
    public void removeLike(Long postId, Long userId, Long roomId) {
        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //회원이 스터디룸에 참여하고 있는지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        //게시글 좋아요 찾기
        RoomTroublePostLike like = roomTroublePostLikeRepository
                .findByRoomTroublePostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus. _POST_LIKE_NOT_FOUND));

        post.removeLike(like);

        //좋아요 찾기
        roomTroublePostLikeRepository.delete(like);
    }

    @Override
    public void addBookmark(Long postId, Long userId, Long roomId) {
        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //회원이 스터디룸에 참여하고 있는지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        //이미 북마크가 돼있다면 에러 발생
        if (roomTroublePostBookmarkRepository.findByRoomTroublePostAndStudyMate(post, studyMate).isPresent()) {
            throw new RestApiException(RoomPostErrorStatus._POST_ALREADY_BOOKMARKED);
        }else { //북마크가 없다면

            //새로운 북마크 생성
            RoomTroublePostBookmark newBookmark = RoomTroublePostBookmark.builder()
                    .roomTroublePost(post)
                    .studyMate(studyMate)
                    .build();
            post.addBookmark(newBookmark);

            //북마크 저장
            roomTroublePostBookmarkRepository.save(newBookmark);
        }
    }

    //TODO: 북마크 제거
    @Override
    public void removeBookmark(Long postId, Long userId, Long roomId) {
        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //회원이 스터디룸에 참여하고 있는지 검증
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, roomId);

        //새로운 북마크 생성
        RoomTroublePostBookmark bookmark = roomTroublePostBookmarkRepository
                .findByRoomTroublePostAndStudyMate(post, studyMate)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_BOOKMARKED_NOT_FOUND));

        post.removeBookmark(bookmark);
        //북마크 제거
        roomTroublePostBookmarkRepository.delete(bookmark);
    }


    //TODO: 게시글 삭제
    @Override
    public void deletePost(Long postId, Long userId) {
        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //게시글 작성자와 삭제하려는 사람이 같은 사람인지 점검
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND);
        }

        // 게시글 삭제
        roomTroublePostRepository.delete(post);
    }

    //TODO: 게시글 업데이트
    @Override
    public void updatePost(Long postId, Long userId, RoomPostRequestDto.RoomTroubloePostDto requestDto) {
        //게시글 찾기
        RoomTroublePost post = roomTroublePostQueryService.findTroublePostById(postId);

        //게시글 작성자와 수정자가 같은지 점검
        if (!post.isAuthor(userId)) {
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_UPDATE_AUTH);
        }

        //게시글 업데이트
        post.update(requestDto.getTitle(), requestDto.getBody());
    }

    //TODO: 댓글 업데이트
    @Override
    public void updateComment(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto requestDto) {
        //댓글 조회
        TroublePostComment comment = roomTroublePostQueryService.findTroublePostCommentById(commentId);

        //댓글 작성자와 수정자가 같은지 점검
        if (!comment.isAuthor(userId)){
            throw new RestApiException(RoomPostErrorStatus._USER_NOT_COMMENT_UPDATE_AUTH);
        }

        //댓글 업데이트
        comment.updateComment(requestDto.getBody());
    }


    //TODO: 댓글 삭제
    @Override
    public void deleteComment(Long commentId, Long userId) {
        //댓글 조회
        TroublePostComment comment = roomTroublePostQueryService.findTroublePostCommentById(commentId);

        //댓글 작성자와 삭제하려는 자가 같은지 점검
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



    //TODO: 답글 추가
    @Override
    public RoomPostResponseDto.toWriteCommentDto addReply(Long commentId, Long userId, RoomPostRequestDto.RoomTroubleCommentDto request) {

        //댓글 조회
        TroublePostComment parentComment = roomTroublePostQueryService.findPostCommentById(commentId);

        //답글일 때 에러 발생
        if (parentComment.isReply()) {
            throw new IllegalStateException("답글에는 답글을 달 수 없습니다.");
        }

        //user 조회
        User user = userQueryService.findUserById(userId);

        //답글 생성
        TroublePostComment reply = TroublePostComment.builder()
                .user(user)
                .roomTroublePost(parentComment.getRoomTroublePost())
                .body(request.getBody())
                .isReply(true)
                .parentComment(parentComment)
                .build();

        parentComment.addReply(reply);

        //답글 저장
        troublePostCommentRepository.save(reply);

        //답글 id DTO로 반환
        return RoomTroublePostConverter.toWriteCommentIdDto(reply);
    }




    //답글 삭제
    @Override
    public void deleteReply(TroublePostComment reply) {
        //댓글 조회
        TroublePostComment parentComment = reply.getParentComment();

        //댓글에 달린 답글 제거
        parentComment.getReplies().remove(reply);

        //답글 삭제
        troublePostCommentRepository.delete(reply);
    }

    @Override
    public void deleteCommentAndReplies(TroublePostComment comment) {
        // CascadeType.ALL과 orphanRemoval = true 설정으로 인해
        // 댓글을 삭제하면 연관된 모든 답글도 자동으로 삭제됩니다.
        troublePostCommentRepository.delete(comment);
    }
}
