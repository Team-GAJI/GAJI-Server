package gaji.service.domain.user.converter;

import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserResponseDTO;

public class UserConverter {
    public static UserResponseDTO.GetUserDetailDTO toGetUserDetailDTO(User user) {
        return UserResponseDTO.GetUserDetailDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImagePth(user.getProfileImagePth())
                .build();
    }
}
