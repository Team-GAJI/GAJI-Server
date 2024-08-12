package gaji.service.domain.user.web.controller;

import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final TokenProviderService tokenProviderService;

    @GetMapping("/{userId}")
    public BaseResponse<UserResponseDTO.GetUserDetailDTO> getUserDetail(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                                                        @PathVariable("userId") Long userId) {

        User user = userQueryService.getUserDetail(userId);
        return BaseResponse.onSuccess(UserConverter.toGetUserDetailDTO(user));
    }
}

