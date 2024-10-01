package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.TroublePostCommentRepository;
import gaji.service.domain.roomBoard.service.postCommon.PostCommonQueryServiceImpl;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTroublePostQueryServiceImpl implements RoomTroublePostQueryService {

    private final TroublePostCommentRepository troublePostCommentRepository;
    private final RoomTroublePostRepository roomTroublePostRepository;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomBoardRepository roomBoardRepository;
    private final PostCommonQueryServiceImpl postCommonQueryServiceImpl;

    //TODO: 댓글 ID로 TroublePostComment 조회
    @Override
    public TroublePostComment findTroublePostCommentById(Long commentId) {
        return troublePostCommentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    //TODO: 다음 게시글 목록 가져오기 (무한 스크롤용)
    @Override
    public List<RoomPostResponseDto.TroublePostSummaryDto> getNextTroublePosts(Long roomId, Long lastPostId, int size) {
        // 룸 보드 조회
        RoomBoard roomBoard = postCommonQueryServiceImpl.findRoomBoardByRoomId(roomId);

        // 마지막 게시글의 생성 시간 결정
        LocalDateTime lastCreatedAt;
        if (lastPostId == 0) {
            lastCreatedAt = LocalDateTime.now();
        } else {
            lastCreatedAt = roomTroublePostRepository.findCreatedAtByIdOrEarliest(roomBoard.getId(), lastPostId)
                    .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        }

        // 페이징 설정
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        // 다음 게시글 목록 조회 및 반환
        return roomTroublePostRepository.findTroublePostSummariesForInfiniteScroll(roomBoard.getId(), lastCreatedAt, pageable);
    }

    //TODO: 게시글 상세 정보 조회
    @Override
    @Transactional
    public RoomPostResponseDto.TroublePostDetailDTO getPostDetail(Long postId, Long userId, int page, int size) {
        // 게시글 조회
        RoomTroublePost post = roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        // 사용자의 StudyMate 정보 조회
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, post.getRoomBoard().getRoom().getId());

        // 조회수 증가
        post.increaseViewCnt();

        // DTO 객체 생성 및 데이터 설정
        RoomPostResponseDto.TroublePostDetailDTO dto = new RoomPostResponseDto.TroublePostDetailDTO();
        // dto 필드 설정...

        // 좋아요, 북마크 상태 설정
        dto.setLiked(post.getRoomTroublePostLikeList().stream()
                .anyMatch(like -> like.getStudyMate().getId().equals(studyMate.getId())));
        dto.setBookmarked(post.getRoomTroublePostBookmarkList().stream()
                .anyMatch(bookmark -> bookmark.getStudyMate().getId().equals(studyMate.getId())));

        // 댓글 및 답글 조회
        Page<RoomPostResponseDto.CommentWithRepliesDTO> comments = getCommentsWithReplies(postId, PageRequest.of(page, size));
        dto.setComments(comments);

        return dto;
    }

    //TODO: 댓글 및 답글 조회
    @Override
    public Page<RoomPostResponseDto.CommentWithRepliesDTO> getCommentsWithReplies(Long postId, Pageable pageable) {
        // 댓글 페이지 조회
        Page<TroublePostComment> commentPage = troublePostCommentRepository.findOldestComments(postId, pageable);

        // 댓글 및 답글 DTO 변환
        List<RoomPostResponseDto.CommentWithRepliesDTO> commentDTOs = commentPage.getContent().stream()
                .map(this::convertToCommentWithRepliesDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(commentDTOs, pageable, commentPage.getTotalElements());
    }

    //TODO: 댓글 ID로 PostComment 조회
    @Override
    public TroublePostComment findPostCommentById(Long commentId){
        return troublePostCommentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    //TODO: 게시글 ID로 RoomTroublePost 조회
    @Override
    public RoomTroublePost findTroublePostById(Long postId){
        return roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
    }

    //TODO: 댓글과 답글을 DTO로 변환
    private RoomPostResponseDto.CommentWithRepliesDTO convertToCommentWithRepliesDTO(TroublePostComment comment) {
        RoomPostResponseDto.CommentWithRepliesDTO dto = new RoomPostResponseDto.CommentWithRepliesDTO();
        // dto 필드 설정...

        // 답글 목록 변환 및 설정
        dto.setReplies(comment.getReplies().stream()
                .sorted(Comparator.comparing(TroublePostComment::getCreatedAt))
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    // 답글을 DTO로 변환
    private RoomPostResponseDto.CommentDTO convertToCommentDTO(TroublePostComment reply) {
        RoomPostResponseDto.CommentDTO dto = new RoomPostResponseDto.CommentDTO();
        // dto 필드 설정...
        return dto;
    }

}

