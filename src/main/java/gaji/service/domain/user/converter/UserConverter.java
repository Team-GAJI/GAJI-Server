package gaji.service.domain.user.converter;

import gaji.service.domain.enums.UserActive;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserResponseDTO;

public class UserConverter {
    public static UserResponseDTO.CancleResultDTO toCancleResultDTO(User user) {
        return UserResponseDTO.CancleResultDTO.builder()
                .userId(user.getId())
                .userActive(user.getStatus())
                .build();
    }

    public static UserResponseDTO.UpdateNicknameResultDTO toUpdateNicknameResultDTO(User user) {
        return UserResponseDTO.UpdateNicknameResultDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

}
