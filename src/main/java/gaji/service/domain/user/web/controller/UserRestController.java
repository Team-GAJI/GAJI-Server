package gaji.service.domain.user.web.controller;

import gaji.service.domain.common.annotation.CheckPage;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final TokenProviderService tokenProviderService;

    @GetMapping("/rooms")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getMyRoomList(@RequestHeader("Authorization") String authorizationHeader,
                                                                      @RequestParam(defaultValue = "3") Integer limit) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        List<Room> userRoomList = userQueryService.getUserRoomList(userId, limit);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }

    @GetMapping("/rooms/{userId}")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getUserRoomList(@PathVariable Long userId,
                                                                        @RequestParam(defaultValue = "3") Integer limit) {
        List<Room> userRoomList = userQueryService.getUserRoomList(userId, limit);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }
}

