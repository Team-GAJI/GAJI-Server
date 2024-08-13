package gaji.service.domain.roomBoard.service;

import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;

import java.util.List;

public interface RoomPostQueryService {
    List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId);

}
