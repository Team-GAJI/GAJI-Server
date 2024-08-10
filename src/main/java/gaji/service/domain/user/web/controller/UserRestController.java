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
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final TokenProviderService tokenProviderService;

    @GetMapping("/rooms")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getMyRoomList(@RequestHeader("Authorization") String authorizationHeader,
                                                                      @RequestParam(value = "cursorDate",required = false) LocalDate cursorDate,
                                                                      @RequestParam(value = "cursorId",required = false) Long cursorId,
                                                                      @RequestParam("type") RoomTypeEnum type,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        Slice<Tuple> userRoomList = userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, size);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }

    @GetMapping("/rooms/{userId}")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getUserRoomList(@PathVariable Long userId,
                                                                        @RequestParam(value = "cursorDate",required = false) LocalDate cursorDate,
                                                                        @RequestParam(value = "cursorId",required = false) Long cursorId,
                                                                        @RequestParam("type") RoomTypeEnum type,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Slice<Tuple> userRoomList = userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, size);
        return BaseResponse.onSuccess(UserConverter.toGetRoomListDTO(userRoomList));
    }

}

