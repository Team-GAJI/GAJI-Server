package gaji.service.domain.roomBoard.web.controller;

import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.service.RoomPost.RoomPostCommandService;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class RoomPostController {

    private final TokenProviderService tokenProviderService;
    private final RoomPostCommandService roomPostCommandService;

    @PostMapping("/room-Post/{roomId}")
    @Operation(summary = "스터디룸 게시글 등록 API")
    public BaseResponse<RoomPostResponseDto.toCreateRoomPostIdDTO> createRoomPostController(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("roomId") Long roomId,
            @RequestBody RoomPostRequestDto.RoomPostDto requestDto
            ){
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        RoomPostResponseDto.toCreateRoomPostIdDTO toCreateRoomPostIdDTO = roomPostCommandService.createRoomPost(roomId, userId, requestDto);
        return BaseResponse.onSuccess(toCreateRoomPostIdDTO);

    }

    @PutMapping("/trouble/{postId}")
    @Operation(summary = "스터디룸 트러블슈팅 게시글 업데이트 API")
    public BaseResponse<String> updatePost(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RoomPostRequestDto.RoomPostDto requestDto,
            @PathVariable Long postId
    ) {
        Long userId = tokenProviderService.getUserIdFromToken(authorization);
        roomPostCommandService.updatePost(postId, userId,requestDto);
        return BaseResponse.onSuccess( "게시글이 성공적으로 업데이트되었습니다.");
    }

}
