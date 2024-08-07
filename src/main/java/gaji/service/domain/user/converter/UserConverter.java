package gaji.service.domain.user.converter;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.converter.DateConverter;

public class UserConverter {
    public static UserResponseDTO.GetPostDTO toGetPostDTO(Post userPost) {
        User user = userPost.getUser();
        return UserResponseDTO.GetPostDTO.builder()
                .postId(userPost.getId())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePth())
                .title(userPost.getTitle())
                .body(userPost.getBody())
                .status(userPost.getStatus())
                .type(userPost.getType())
                .createdAt(DateConverter.convertToRelativeTimeFormat(userPost.getCreatedAt()))
                .build();
    }
}
