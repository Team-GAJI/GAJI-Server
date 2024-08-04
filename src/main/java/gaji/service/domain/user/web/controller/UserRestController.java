package gaji.service.domain.user.web.controller;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserRequestDTO;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
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

    @PutMapping("/")
    public BaseResponse<UserResponseDTO.CancleResultDTO> cancle(Long userId/*하드 코딩용 추후 수정.*/) {
        //Long myId = getUserIdFromToken(token);

        User user = userCommandService.cancleUser(userId);
        return BaseResponse.onSuccess(UserConverter.toCancleResultDTO(user));
    }


    @PutMapping("/nicknames")
    public BaseResponse<UserResponseDTO.UpdateNicknameResultDTO> updateNickname(Long userId/*하드 코딩용 추후 수정.*/,
                                                                                @RequestBody @Valid UserRequestDTO.UpdateNicknameDTO request
                                                                                ) {
        //Long myId = getUserIdFromToken(token);

        User user = userCommandService.updateUserNickname(userId, request);
        return BaseResponse.onSuccess(UserConverter.toUpdateNicknameResultDTO(user));
    }
}

