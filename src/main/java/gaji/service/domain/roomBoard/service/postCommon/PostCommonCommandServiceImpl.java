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

    // TODO: 게시글 댓글 저장
    @Override
    public void savePostComment(PostComment postComment){
        postCommentRepository.save(postComment);
    }

    // TODO: 게시글 답글 삭제
    @Override
    public void deleteReply(PostComment reply) {
        PostComment parentComment = reply.getParentComment();
        parentComment.getReplies().remove(reply);
        postCommentRepository.delete(reply);
    }

    // TODO: 댓글과 답글 삭제
    @Override
    public void deleteCommentAndReplies(PostComment comment) {
        // CascadeType.ALL과 orphanRemoval = true 설정으로 인해
        // 댓글을 삭제하면 연관된 모든 답글도 자동으로 삭제됩니다.
        postCommentRepository.delete(comment);
    }
}
