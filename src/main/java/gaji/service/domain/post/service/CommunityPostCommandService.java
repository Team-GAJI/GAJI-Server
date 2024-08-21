package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.web.dto.CommunityPostRequestDTO;

public interface CommunityPostCommandService {

    CommnuityPost uploadPost(Long userId, CommunityPostRequestDTO.UploadPostRequestDTO request);
    CommunityComment writeCommentOnCommunityPost(Long userId, Long postId, Long parentCommentId, CommunityPostRequestDTO.WriteCommentRequestDTO request);
    void hardDeleteComment(Long userId, Long commentId);
    void hardDeleteCommunityPost(Long userId, Long postId);
    PostBookmark bookmarkCommunityPost(Long userId, Long postId);
    void cancelbookmarkCommunityPost(Long userId, Long postId);
    PostLikes likeCommunityPost(Long userId, Long postId);
    void cancelLikeCommunityPost(Long userId, Long postId);

}
