package gaji.service.domain.user.web.controller;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.RoomTypeEnum;
import gaji.service.domain.user.converter.UserConverter;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.domain.user.web.dto.UserRequestDTO;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @GetMapping("/{userId}")
    public BaseResponse<UserResponseDTO.GetUserDetailDTO> getUserDetail(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                                                        @PathVariable("userId") Long userId) {

        User user = userQueryService.getUserDetail(userId);
        return BaseResponse.onSuccess(UserConverter.toGetUserDetailDTO(user));
    }

    @GetMapping("/posts/{userId}")
    public BaseResponse<UserResponseDTO.GetPostListDTO> getUserPostList(@PathVariable Long userId,
                                                                        @RequestParam(value = "cursorDate",required = false) LocalDateTime cursorDateTime,
                                                                        @RequestParam(value = "type", required = false) PostTypeEnum type,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Slice<Tuple> userPostList = userQueryService.getUserPostList(userId, cursorDateTime, type, size);
        return BaseResponse.onSuccess(UserConverter.toGetPostListDTO(userPostList, type));
    }
}

