package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.repository.RoomTrouble.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.repository.RoomTrouble.TroublePostCommentRepository;
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
public class RoomTroublePostQueryServiceImpl implements RoomTroublePostQueryService {

    private final TroublePostCommentRepository troublePostCommentRepository;
    private final RoomTroublePostRepository roomTroublePostRepository;
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
}
