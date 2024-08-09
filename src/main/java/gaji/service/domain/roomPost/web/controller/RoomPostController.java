package gaji.service.domain.roomPost.web.controller;

import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.service.RoomPostCommandServiceImpl;
import gaji.service.domain.roomPost.service.RoomPostQueryService;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomPost.web.dto.RoomPostResponseDto;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRooms")
public class RoomPostController {
    private final RoomPostQueryService roomPostQueryService;


    @GetMapping("/api/studyRooms/post/{roomId}")
    @Operation(summary = "스터디룸 main 화면 게시글 조회 API")
    public BaseResponse<List<RoomPostResponseDto.PostListDto>> MainRoomPostController(@PathVariable Long roomId){
        return BaseResponse.onSuccess(roomPostQueryService.getTop3RecentPosts(roomId));

    }

}


