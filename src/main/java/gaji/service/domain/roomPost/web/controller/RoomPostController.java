package gaji.service.domain.roomPost.web.controller;

import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.service.RoomPostCommandServiceImpl;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomPostController {

    private final RoomPostCommandServiceImpl roomBoardCommandService;

    @PostMapping(value = "/post/{roomId}/{userId}")
    @Operation(summary = "스터디룸 게시글 등록 API", description = "스터디룸의 게시글을 등록하는 API입니다. room의 id가 존재하는지, 스터디에 참여하고 있는 user인지 검증합니다.")
    public BaseResponse<Long> RoomBoardController(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody @Valid RoomPostRequestDto.RoomPostDto requestDto) {
        RoomPost roomPost = roomBoardCommandService.createRoomPost(roomId, userId, requestDto);
        return BaseResponse.onSuccess(roomPost.getId());
    }
}


