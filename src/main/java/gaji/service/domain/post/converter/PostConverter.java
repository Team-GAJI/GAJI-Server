package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;

public class PostConverter {

    // 초기 PostStatus 지정
    public static PostStatusEnum getInitialPostStatus(PostTypeEnum type) {
        return (type == PostTypeEnum.QUESTION) ? PostStatusEnum.NEED_RESOLUTION :
                (type == PostTypeEnum.PROJECT_RECRUITMENT) ? PostStatusEnum.RECRUITING : PostStatusEnum.POSTING;
    }

    public static Post toPost(PostRequestDTO.UploadPostDTO request, User user) {
        return Post.builder()
                .user(user)
                .title(request.getTitle())
                .body(request.getBody())
                .type(request.getType())
                .status(getInitialPostStatus(request.getType()))
                .build();
    }

    public static Comment toComment(PostRequestDTO.WriteCommentDTO request, User user, Post post, Comment parentComment) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parent(parentComment)
                .body(request.getBody())
                .build();
    }

    public static PostBookmark toPostBookmark(User user, Post post) {
        return PostBookmark.builder()
                .user(user)
                .post(post)
                .build();
    }

    public static PostLikes toPostLikes(User user, Post post) {
        return PostLikes.builder()
                .user(user)
                .post(post)
                .build();
    }
}
