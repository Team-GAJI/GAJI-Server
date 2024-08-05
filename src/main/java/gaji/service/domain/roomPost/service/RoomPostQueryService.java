package gaji.service.domain.roomPost.service;

import gaji.service.domain.roomPost.web.dto.RoomPostResponseDto;

import java.util.List;

public interface RoomPostQueryService {
    List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId);

    List<RoomPostResponseDto.PostListDto> getTop3RecentPosts();
}
