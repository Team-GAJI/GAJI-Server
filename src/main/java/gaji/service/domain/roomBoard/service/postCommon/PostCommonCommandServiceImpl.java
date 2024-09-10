package gaji.service.domain.roomBoard.service.postCommon;

import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.repository.RoomPost.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostCommonCommandServiceImpl implements PostCommonCommandService{
    private final PostCommentRepository postCommentRepository;

    @Override
    public void savePostComment(PostComment postComment){
        postCommentRepository.save(postComment);
    }
}
