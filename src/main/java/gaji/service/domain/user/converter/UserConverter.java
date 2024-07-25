package gaji.service.domain.user.converter;

public class UserConverter {

    public static UserResponseDTO.LogoutResultDTO toLogoutResultDTO(User user){
        return UserResponseDTO.LogoutResultDTO.builder()
                .userId(user.getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }

}
