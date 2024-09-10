package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommonQueryServiceImpl implements PostCommonQueryService{

    private final PostCommentRepository postCommentRepository;
    @Override
    public PostComment findCommentByCommentId(Long commentId){
        return postCommentRepository.findById(commentId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._NOT_FOUND_COMMENT));
    }

}
