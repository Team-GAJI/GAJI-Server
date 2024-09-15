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
import io.swagger.v3.oas.annotations.Operation;
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

    @PutMapping()
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API")
    public BaseResponse<UserResponseDTO.CancleResultDTO> cancle(@RequestHeader("Authorization") String authorizationHeader) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        return BaseResponse.onSuccess(userCommandService.cancleUser(userId));
    }


    @PutMapping("/nicknames/{userId}")
    @Operation(summary = "닉네임 변경 API", description = "닉네임 변경 API")
    public BaseResponse<UserResponseDTO.UpdateNicknameResultDTO> updateNickname(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @PathVariable("userId") Long userIdFromPathVariable,
                                                                                @RequestBody @Valid UserRequestDTO.UpdateNicknameDTO request) {

        Long userIdFromToken = tokenProviderService.getUserIdFromToken(authorizationHeader);

        return BaseResponse.onSuccess(userCommandService.updateUserNickname(userIdFromToken, userIdFromPathVariable, request));
    }

    @GetMapping("/rooms")
    @Operation(summary = "내 방 목록 조회 API", description = "내 방 목록 조회 API")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getMyRoomList(@RequestHeader("Authorization") String authorizationHeader,
                                                                      @RequestParam(value = "cursorDate",required = false) LocalDate cursorDate,
                                                                      @RequestParam(value = "cursorId",required = false) Long cursorId,
                                                                      @RequestParam("type") RoomTypeEnum type,
                                                                      @RequestParam(defaultValue = "10") int size) {

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        return BaseResponse.onSuccess(userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, size));
    }

    @GetMapping("/rooms/{userId}")
    @Operation(summary = "유저 방 목록 조회 API", description = "유저 방 목록 조회 API")
    public BaseResponse<UserResponseDTO.GetRoomListDTO> getUserRoomList(@PathVariable Long userId,
                                                                        @RequestParam(value = "cursorDate",required = false) LocalDate cursorDate,
                                                                        @RequestParam(value = "cursorId",required = false) Long cursorId,
                                                                        @RequestParam("type") RoomTypeEnum type,
                                                                        @RequestParam(defaultValue = "10") int size) {

        return BaseResponse.onSuccess(userQueryService.getUserRoomList(userId, cursorDate, cursorId, type, size));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "유저 상세 조회 API", description = "유저 상세 조회 API")
    public BaseResponse<UserResponseDTO.GetUserDetailDTO> getUserDetail(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                                                        @PathVariable("userId") Long userId) {

        return BaseResponse.onSuccess(userQueryService.getUserDetail(userId));
    }

    @GetMapping("/posts/{userId}")
    @Operation(summary = "유저 게시글 목록 조회 API", description = "유저 게시글 목록 조회 API")
    public BaseResponse<UserResponseDTO.GetPostListDTO> getUserPostList(@PathVariable Long userId,
                                                                        @RequestParam(value = "cursorDate",required = false) LocalDateTime cursorDateTime,
                                                                        @RequestParam(value = "type", required = false) PostTypeEnum type,
                                                                        @RequestParam(defaultValue = "10") int size) {

        return BaseResponse.onSuccess(userQueryService.getUserPostList(userId, cursorDateTime, type, size));
    }
}

