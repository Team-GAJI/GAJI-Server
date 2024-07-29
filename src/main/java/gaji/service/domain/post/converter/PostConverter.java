package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;

public class PostConverter {

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

}
