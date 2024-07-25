package gaji.service.domain.user.controller;

import gaji.service.domain.user.User;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PutMapping("/")
    public ApiResponse<UserResponseDTO.JoinResultDTO> logout(Long userId //하드코딩 한것 추후 user 객체로 수정){
        User user = userCommandService.logoutUser(userId);
        return ApiResponse.onSuccess(UserConverter.toLogoutResultDTO(user));
    }
}
