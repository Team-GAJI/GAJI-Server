package gaji.service.domain.user.web.controller;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.annotation.ExistPostType;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final TokenProviderService tokenProviderService;

    @GetMapping("/posts")
    public BaseResponse<UserResponseDTO.GetPostDTO> getMyPost(@RequestHeader("Authorization") String authorizationHeader,
                                                              @RequestParam("type") @ExistPostType PostTypeEnum type) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        Post userPost = userQueryService.getUserPost(userId,type);
        return BaseResponse.onSuccess(UserConverter.toGetPostDTO(userPost));
    }

    @GetMapping("/posts/{userId}")
    public BaseResponse<UserResponseDTO.GetPostDTO> getUserPost(@PathVariable Long userId,
                                                                @RequestParam("type") @ExistPostType PostTypeEnum type) {
        Post userPost = userQueryService.getUserPost(userId,type);
        return BaseResponse.onSuccess(UserConverter.toGetPostDTO(userPost));
    }
}

