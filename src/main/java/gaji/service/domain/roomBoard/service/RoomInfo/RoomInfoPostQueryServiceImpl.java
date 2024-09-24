package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.converter.RoomInfoPostConverter;
import gaji.service.domain.roomBoard.converter.RoomPostConverter;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.InfoPostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
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
public class RoomInfoPostQueryServiceImpl implements RoomInfoPostQueryService{

    private final RoomInfoPostRepository roomInfoPostRepository;
    private final InfoPostCommentRepository infoPostCommentRepository;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomBoardRepository roomBoardRepository;


    @Override
    public List<RoomPostResponseDto.InfoPostSummaryDto> getNextPosts(Long roomId, Long lastPostId, int size) {
        // 주어진 roomId와 ROOM_POST 타입에 해당하는 RoomBoard를 찾음
        // 찾지 못할 경우 RestApiException 발생
        RoomBoard roomBoard = roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_INFORMATION_POST)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._ROOM_BOARD_NOT_FOUND));

        LocalDateTime lastCreatedAt;
        // 첫 요청일 경우 현재 시간을 기준으로 함
        if (lastPostId == 0) {
            lastCreatedAt = LocalDateTime.now();
        } else {
            // lastPostId에 해당하는 게시물의 생성 시간을 찾음
            // 해당 게시물이 없으면 RestApiException 발생
            lastCreatedAt = roomInfoPostRepository.findCreatedAtByIdOrEarliest(roomBoard.getId(), lastPostId)
                    .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        }

        // 페이지네이션 설정: 요청된 크기만큼 가져오며, 생성일 및 ID 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        List<RoomPostResponseDto.InfoPostSummaryDto> infoPostDto = roomInfoPostRepository.findInfoPostSummariesForInfiniteScroll(roomBoard.getId(), lastCreatedAt, pageable);
        // 조회된 게시물 엔티티를 DTO로 변환하여 반환
        return infoPostDto;
    }


    //TODO: 게시물 상세 조회
    @Override
    @Transactional
    public RoomPostResponseDto.RoomInfoPostDetailDTO getPostDetail(Long postId, Long userId, int page, int size) {
        //게시글 찾기
        RoomInfoPost post = findInfoPostById(postId);

        // 스터디룸에 참여하고 있는 회원 찾기
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, post.getRoomBoard().getRoom().getId());

        // 게시글 조회 수 증가
        post.increaseViewCnt();

        // 댓글 목록 가져오기
        Page<RoomPostResponseDto.CommentWithRepliesDTO> comments = getCommentsWithReplies(postId, PageRequest.of(page, size));

        return RoomInfoPostConverter.toRoomInfoPostDetailDTO(post,studyMate,comments);
    }


    //TODO: 댓글과 답글 조회하기
    @Override
    public Page<RoomPostResponseDto.CommentWithRepliesDTO> getCommentsWithReplies(Long postId, Pageable pageable) {

        //댓글 조회
        Page<InfoPostComment> commentPage = infoPostCommentRepository.findOldestComments(postId, pageable);

        //댓글 DTO로 변환
        List<RoomPostResponseDto.CommentWithRepliesDTO> commentDTOs = commentPage.getContent().stream()
                .map(this::convertToCommentWithRepliesDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(commentDTOs, pageable, commentPage.getTotalElements());
    }

    //댓글과 답글을 DTO로 변환
    private RoomPostResponseDto.CommentWithRepliesDTO convertToCommentWithRepliesDTO(InfoPostComment comment) {
        RoomPostResponseDto.CommentWithRepliesDTO dto = new RoomPostResponseDto.CommentWithRepliesDTO();
        dto.setId(comment.getId());
        dto.setUserNickName(comment.getUser().getName());
        dto.setCommentBody(comment.getBody());
        dto.setCommentWriteDate(comment.getCreatedAt());
        dto.setReplies(comment.getReplies().stream()
                .sorted(Comparator.comparing(InfoPostComment::getCreatedAt))
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    //댓글을 DTO로 변환
    private RoomPostResponseDto.CommentDTO convertToCommentDTO(InfoPostComment reply) {
        RoomPostResponseDto.CommentDTO dto = new RoomPostResponseDto.CommentDTO();
        dto.setId(reply.getId());
        dto.setUserNickName(reply.getUser().getName());
        dto.setCommentBody(reply.getBody());
        dto.setCommentWriteDate(reply.getCreatedAt());
        return dto;
    }






    //TODO: 댓글 조회
    @Override
    public InfoPostComment findInfoParentComment(Long commentId){
        return infoPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    //TODO: 게시글 조회
    @Override
    public RoomInfoPost findInfoPostById(Long PostId){
        return roomInfoPostRepository.findById(PostId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_NOT_FOUND));
    }


    @Override
    public InfoPostComment findPostCommentById(Long troublePostId) {
        return infoPostCommentRepository.findById(troublePostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }
}
