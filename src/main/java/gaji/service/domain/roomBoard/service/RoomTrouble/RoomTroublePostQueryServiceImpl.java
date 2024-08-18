package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.TroublePostCommentRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTroublePostQueryServiceImpl implements RoomTroublePostQueryService {

    private final TroublePostCommentRepository troublePostCommentRepository;
    private final RoomTroublePostRepository roomTroublePostRepository;
    private final StudyMateQueryService studyMateQueryService;

    @Override
    public TroublePostComment findCommentByCommentId(Long commentId){
        return troublePostCommentRepository.findById(commentId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public TroublePostComment findTroublePostCommentById(Long troublePostId) {
        return troublePostCommentRepository.findById(troublePostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public List<RoomPostResponseDto.TroublePostSummaryDto> getNextTroublePosts(Long boardId, Long lastPostId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return roomTroublePostRepository.findTroublePostSummariesForInfiniteScroll(boardId, lastPostId,pageable);
    }



    @Override
    public RoomPostResponseDto.TroublePostDetailDTO getPostDetail(Long postId, Long userId) {
        RoomTroublePost post = roomTroublePostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, post.getRoomBoard().getRoom().getId());

        RoomPostResponseDto.TroublePostDetailDTO dto = new RoomPostResponseDto.TroublePostDetailDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setAuthorName(post.getStudyMate().getUser().getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setViewCount(post.getViewCount());
        dto.setLikeCount(post.getLikeCount());
        dto.setBookmarkCount(post.getBookmarkCount());
        dto.setLiked(post.getRoomTroublePostLikeList().stream()
                .anyMatch(like -> like.getStudyMate().getId().equals(studyMate.getId())));
        dto.setBookmarked(post.getRoomTroublePostBookmarkList().stream()
                .anyMatch(bookmark -> bookmark.getStudyMate().getId().equals(studyMate.getId())));

        List<RoomPostResponseDto.CommentDTO> comments = getCommentsWithReplies(post.getId());
        dto.setComments(comments);

        return dto;
    }

    private List<RoomPostResponseDto.CommentDTO> getCommentsWithReplies(Long postId) {
        List<TroublePostComment> comments = troublePostCommentRepository.findByRoomTroublePostIdAndIsReplyFalse(postId);
        return comments.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }

    private RoomPostResponseDto.CommentDTO convertToCommentDTO(TroublePostComment comment) {
        RoomPostResponseDto.CommentDTO dto = new RoomPostResponseDto.CommentDTO();
        dto.setId(comment.getId());
        dto.setAuthorName(comment.getUser().getName());
        dto.setBody(comment.getBody());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setReplies(comment.getReplies().stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public List<RoomPostResponseDto.CommentDTO> getMoreComments(Long postId, Long lastCommentId, int size) {
        List<TroublePostComment> comments = troublePostCommentRepository
                .findMoreComments(postId, lastCommentId, PageRequest.of(0, size));
        return comments.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomPostResponseDto.CommentDTO> getMoreReplies(Long commentId, Long lastReplyId, int size) {
        List<TroublePostComment> replies = troublePostCommentRepository
                .findMoreReplies(commentId, lastReplyId, PageRequest.of(0, size));
        return replies.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }
}
