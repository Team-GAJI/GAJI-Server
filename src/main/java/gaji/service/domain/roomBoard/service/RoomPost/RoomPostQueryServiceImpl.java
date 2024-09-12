package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.enums.RoomPostType;
import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPostBookmark;
import gaji.service.domain.roomBoard.repository.RoomBoardRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostBookmarkRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostQueryRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.global.converter.DateConverter;
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
public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private final RoomPostQueryRepository roomPostQueryRepository;
    private final RoomPostRepository roomPostRepository;
    private final PostCommentRepository postCommentRepository;
    private final StudyMateQueryService studyMateQueryService;
    private final RoomBoardRepository roomBoardRepository;
    private final RoomPostBookmarkRepository roomPostBookmarkRepository;

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId) {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo(roomId);
    }

    // TODO: 최신 3개의 공지사항 불러오기
    @Override
    public List<RoomPostResponseDto.MainPostSummaryDto> getLatestPosts(Long roomId) {

        // 페이지네이션 설정: 첫 페이지에서 3개의 항목을 가져오며, 생성일 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 주어진 roomId와 ROOM_POST 타입에 해당하는 RoomBoard를 찾음
        // 찾지 못할 경우 RestApiException 발생
        RoomBoard roomBoard = roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_POST)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._ROOM_BOARD_NOT_FOUND));

        // 찾은 RoomBoard의 ID를 사용하여 최신 게시물 요약 정보를 가져옴
        List<RoomPostResponseDto.MainPostSummaryDto> posts = roomPostRepository.findLatestPostsSummary(roomBoard.getId(), pageable);

        // 현재 시간 기준으로 각 게시물의 상대적 시간 형식 설정
        LocalDateTime now = LocalDateTime.now();
        for (RoomPostResponseDto.MainPostSummaryDto post : posts) {
            post.setTimeSincePosted(DateConverter.convertToRelativeTimeFormat(post.getCreatedAt()));
        }

        // 처리된 게시물 목록 반환
        return posts;
    }

    // TODO: 무한 스크롤 기능 구현
    @Override
    public List<RoomPostResponseDto.PostSummaryDto> getNextPosts(Long roomId, Long lastPostId, int size) {

        // 주어진 roomId와 ROOM_POST 타입에 해당하는 RoomBoard를 찾음
        // 찾지 못할 경우 RestApiException 발생
        RoomBoard roomBoard = roomBoardRepository.findRoomBoardByRoomIdAndRoomPostType(roomId, RoomPostType.ROOM_POST)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._ROOM_BOARD_NOT_FOUND));

        LocalDateTime lastCreatedAt;
        if (lastPostId == 0) {
            // 첫 요청일 경우 현재 시간을 기준으로 함
            lastCreatedAt = LocalDateTime.now();
        } else {
            // lastPostId에 해당하는 게시물의 생성 시간을 찾음
            // 해당 게시물이 없으면 RestApiException 발생
            lastCreatedAt = roomPostRepository.findCreatedAtByIdOrEarliest(roomBoard.getId(), lastPostId)
                    .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        }

        // 페이지네이션 설정: 요청된 크기만큼 가져오며, 생성일 및 ID 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        // 무한 스크롤을 위한 다음 게시물 목록을 조회하여 반환
        return roomPostRepository.findPostSummariesForInfiniteScroll(roomBoard.getId(), lastCreatedAt, pageable);
    }

    // TODO: id 로 roomPost 찾기
    @Override
    public RoomPost findPostById(Long PostId){
        return roomPostRepository.findById(PostId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_NOT_FOUND));
    }

    // TODO: id로 roomPost 북마크 조회
    @Override
    public RoomPostBookmark findRoomPostBookmarkByRoomPostAndStudyMate(RoomPost post, StudyMate studyMate){
        return roomPostBookmarkRepository.findByRoomPostAndStudyMate(post, studyMate)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_BOOKMARKED_NOT_FOUND));

    }

    // TODO: 게시글 댓글 조회
    @Override
    public PostComment findPostCommentById(Long troublePostId) {
        return postCommentRepository.findById(troublePostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }


    // TODO: 게시물 상세 조회
    @Override
    @Transactional
    public RoomPostResponseDto.RoomPostDetailDTO getPostDetail(Long postId, Long userId, int page, int size) {
        // 주어진 postId로 게시물을 찾음. 없으면 예외 발생
        RoomPost post = roomPostRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));

        // userId와 게시물의 방 ID로 StudyMate 정보를 조회
        StudyMate studyMate = studyMateQueryService.findByUserIdAndRoomId(userId, post.getRoomBoard().getRoom().getId());

        // 게시물 조회수 증가
        post.increaseViewCnt();

        // 게시물 상세 정보를 담을 DTO 객체 생성
        RoomPostResponseDto.RoomPostDetailDTO dto = new RoomPostResponseDto.RoomPostDetailDTO();

        // DTO에 게시물 정보 설정
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setAuthorName(post.getStudyMate().getUser().getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setViewCount(post.getViewCount());
        dto.setLikeCount(post.getLikeCount());
        dto.setBookmarkCount(post.getBookmarkCount());

        // 현재 사용자가 게시물에 좋아요를 눌렀는지 확인
        dto.setLiked(post.getRoomPostLikesList().stream()
                .anyMatch(like -> like.getStudyMate().getId().equals(studyMate.getId())));

        // 현재 사용자가 게시물을 북마크했는지 확인
        dto.setBookmarked(post.getRoomPostBookmarkList().stream()
                .anyMatch(bookmark -> bookmark.getStudyMate().getId().equals(studyMate.getId())));

        // 게시물의 댓글과 대댓글을 페이지네이션하여 조회
        Page<RoomPostResponseDto.CommentWithRepliesDTO> comments = getCommentsWithReplies(postId, PageRequest.of(page, size));
        dto.setComments(comments);

        // 완성된 DTO 반환
        return dto;
    }

    // TODO: 댓글 조회 기능 구현
    @Override
    public Page<RoomPostResponseDto.CommentWithRepliesDTO> getCommentsWithReplies(Long postId, Pageable pageable) {
        // 주어진 postId에 해당하는 게시물의 댓글들을 페이지네이션하여 조회
        // 가장 오래된 댓글부터 정렬됨
        Page<PostComment> commentPage = postCommentRepository.findOldestComments(postId, pageable);

        // 조회된 댓글들을 DTO로 변환
        // convertToCommentWithRepliesDTO 메서드를 사용하여 각 댓글과 그에 달린 대댓글 정보를 포함한 DTO로 변환
        List<RoomPostResponseDto.CommentWithRepliesDTO> commentDTOs = commentPage.getContent().stream()
                .map(this::convertToCommentWithRepliesDTO)
                .collect(Collectors.toList());

        // 변환된 DTO 리스트를 사용하여 새로운 Page 객체 생성 및 반환
        // 원본 페이지의 페이지네이션 정보와 전체 요소 수를 유지
        return new PageImpl<>(commentDTOs, pageable, commentPage.getTotalElements());
    }


    // TODO: 댓글과 답글을 dto로 변환
    private RoomPostResponseDto.CommentWithRepliesDTO convertToCommentWithRepliesDTO(PostComment comment) {
        // 댓글과 그에 달린 대댓글 정보를 포함하는 DTO 객체 생성
        RoomPostResponseDto.CommentWithRepliesDTO dto = new RoomPostResponseDto.CommentWithRepliesDTO();

        // 댓글의 기본 정보 설정
        dto.setId(comment.getId());
        dto.setUserNickName(comment.getUser().getName());
        dto.setCommentBody(comment.getBody());
        dto.setCommentWriteDate(comment.getCreatedAt());

        // 대댓글 목록을 DTO로 변환하여 설정
        // 대댓글은 생성 시간 순으로 정렬됨
        dto.setReplies(comment.getReplies().stream()
                .sorted(Comparator.comparing(PostComment::getCreatedAt))
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private RoomPostResponseDto.CommentDTO convertToCommentDTO(PostComment reply) {
        // 단일 댓글(대댓글) 정보를 담는 DTO 객체 생성
        RoomPostResponseDto.CommentDTO dto = new RoomPostResponseDto.CommentDTO();

        // 댓글의 기본 정보 설정
        dto.setId(reply.getId());
        dto.setUserNickName(reply.getUser().getName());
        dto.setCommentBody(reply.getBody());
        dto.setCommentWriteDate(reply.getCreatedAt());

        return dto;
    }

    // TODO: roomPost 조회
    @Override
    public RoomPost findRoomPostById(Long roomPostId){
        // roomPost 조회 및 에러처리
        RoomPost post = roomPostRepository.findById(roomPostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._POST_NOT_FOUND));
        return post;
    }

}
