package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.repository.RoomInfo.InfoPostCommentRepository;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
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
public class RoomInfoPostQueryServiceImpl implements RoomInfoPostQueryService{

    private final RoomInfoPostRepository roomInfoPostRepository;
    private final InfoPostCommentRepository infoPostCommentRepository;

    @Override
    public RoomInfoPost findInfoPostById(Long PostId){
        return roomInfoPostRepository.findById(PostId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_NOT_FOUND));
    }


    @Override
    public InfoPostComment findCommentByCommentId(Long commentId){
        return infoPostCommentRepository.findById(commentId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public InfoPostComment findPostCommentById(Long troublePostId) {
        return infoPostCommentRepository.findById(troublePostId)
                .orElseThrow(() -> new RestApiException(RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

    @Override
    public List<RoomPostResponseDto.InfoPostSummaryDto> getNextPosts(Long boardId, Long lastPostId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return roomInfoPostRepository.findInfoPostSummariesForInfiniteScroll(boardId, lastPostId,pageable);
    }
}
