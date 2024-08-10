package gaji.service.domain.user.web.controller;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                                                                      @RequestParam("cursorDate") LocalDate cursorDate,
                                                                      @RequestParam("cursorId") Long cursorId,
                                                                      @RequestParam("type") RoomTypeEnum type,
                                                                      @RequestParam(defaultValue = "3") Integer limit) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        List<Tuple> userRoomList = userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, limit);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }

    @GetMapping("/rooms/{userId}")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getUserRoomList(@PathVariable Long userId,
                                                                        @RequestParam("cursorDate") LocalDate cursorDate,
                                                                        @RequestParam("cursorId") Long cursorId,
                                                                        @RequestParam("type") RoomTypeEnum type,
                                                                        @RequestParam(defaultValue = "3") Integer limit) {
        List<Tuple> userRoomList = userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, limit);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }

}

