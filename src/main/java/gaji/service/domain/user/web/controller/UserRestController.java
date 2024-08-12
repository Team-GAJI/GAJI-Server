package gaji.service.domain.user.web.controller;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserRequestDTO;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.global.exception.RestApiException;
import gaji.service.jwt.service.TokenProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final TokenProviderService tokenProviderService;

    @PutMapping("/")
    public BaseResponse<UserResponseDTO.CancleResultDTO> cancle(@RequestHeader("Authorization") String authorizationHeader) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        User user = userCommandService.cancleUser(userId);
        return BaseResponse.onSuccess(UserConverter.toCancleResultDTO(user));
    }


    @PutMapping("/nicknames/{userId}")
    public BaseResponse<UserResponseDTO.UpdateNicknameResultDTO> updateNickname(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @PathVariable("userId") Long userIdFromPathVariable,
                                                                                @RequestBody @Valid UserRequestDTO.UpdateNicknameDTO request) {


        Long userIdFromToken = tokenProviderService.getUserIdFromToken(authorizationHeader);
        User user = userCommandService.updateUserNickname(userIdFromToken, userIdFromPathVariable, request);
        return BaseResponse.onSuccess(UserConverter.toUpdateNicknameResultDTO(user));
    }
}

