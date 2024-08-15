package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.web.dto.PostRequestDTO;

public interface PostCommandService {

    Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request);
    Comment writeCommentOnCommunityPost(Long userId, Long postId, Long parentCommentId, PostRequestDTO.WriteCommentDTO request);
    void hardDeleteComment(Long commentId);
    void hardDeleteCommunityPost(Long postId);
    PostBookmark bookmarkCommunityPost(Long userId, Long postId);
    void cancelbookmarkCommunityPost(Long userId, Long postId);
    PostLikes likeCommunityPost(Long userId, Long postId);
    void cancelLikeCommunityPost(Long userId, Long postId);

}
