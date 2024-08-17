package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.roomBoard.service.RoomInfo.RoomInfoPostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomInfoPostController {
    private final TokenProviderService tokenProviderService;
    private final RoomInfoPostCommandService roomInfoPostCommandService;

    @PostMapping("/info-post/{roomId}")
    @Operation(summary = "스터디룸 정보나눔 게시판 등록 API")
    public BaseResponse<RoomPostResponseDto.toCreateRoomInfoPostIdDTO> StudyRoomInfoPostController(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid RoomPostRequestDto.RoomInfoPostDto requestDto,
            @PathVariable Long roomId
    ){

        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toCreateRoomInfoPostIdDTO roomInfoPostIdDTO = roomInfoPostCommandService.createRoomInfoPostIdDTO(roomId, userId, requestDto);
        return BaseResponse.onSuccess(roomInfoPostIdDTO);
    }

    @PutMapping("/info/{postId}")
    @Operation(summary = "스터디룸 정보나눔 게시글 업데이트 API")
    public BaseResponse<String> updatePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomInfoPostDto requestDto,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomInfoPostCommandService.updateInfoPost(postId, userId,requestDto);
        return BaseResponse.onSuccess( "게시글이 성공적으로 업데이트되었습니다.");
    }
}
