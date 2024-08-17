package gaji.service.domain.roomBoard.service.RoomTrouble;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.repository.RoomTrouble.TroublePostCommentRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomTroublePostQueryServiceImpl implements RoomTroublePostQueryService {

    private final TroublePostCommentRepository troublePostCommentRepository;
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
}
