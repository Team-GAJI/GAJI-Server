package gaji.service.domain.roomBoard.service.RoomPost;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostQueryRepository;
import gaji.service.domain.roomBoard.repository.RoomPost.RoomPostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private final RoomPostQueryRepository roomPostQueryRepository;
    private final RoomTroublePostRepository roomTroublePostRepository;
    private final RoomPostRepository roomPostRepository;
    private final PostCommentRepository postCommentRepository;

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId) {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo(roomId);
    }

    @Override
    public List<RoomPostResponseDto.TroublePostSummaryDto> getNextTroublePosts(Long boardId, Long lastPostId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return roomTroublePostRepository.findTroublePostSummariesForInfiniteScroll(boardId, lastPostId,pageable);
    }

    @Override
    public RoomPost findPostById(Long PostId){
        return roomPostRepository.findById(PostId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_NOT_FOUND));
    }

    @Override
    public PostComment findCommentByCommentId(Long commentId){
        return postCommentRepository.findById(commentId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public PostComment findPostCommentById(Long troublePostId) {
        return postCommentRepository.findById(troublePostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }
}
