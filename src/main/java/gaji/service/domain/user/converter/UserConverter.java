package gaji.service.domain.user.converter;

import gaji.service.domain.user.User;
import gaji.service.domain.user.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class UserConverter {

    public static UserResponseDTO.LogoutResultDTO toLogoutResultDTO(User user){
        return UserResponseDTO.LogoutResultDTO.builder()
                .userId(user.getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
