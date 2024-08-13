package gaji.service.domain.roomBoard.web.controller;


import gaji.service.domain.roomBoard.service.RoomPostQueryService;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomPostController {
    private final RoomPostQueryService roomPostQueryService;
    private TokenProviderService tokenProviderService;

    @GetMapping("/api/studyRooms/post/{roomId}")
    @Operation(summary = "스터디룸 main 화면 게시글 조회 API")
    public BaseResponse<List<RoomPostResponseDto.PostListDto>> MainRoomPostController(
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String authorizationHeader){

        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        return BaseResponse.onSuccess(roomPostQueryService.getTop3RecentPosts(roomId));

    }


}


