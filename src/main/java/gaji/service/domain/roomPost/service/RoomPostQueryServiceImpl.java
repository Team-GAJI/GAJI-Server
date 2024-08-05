package gaji.service.domain.roomPost.service;

import gaji.service.domain.room.repository.RoomQueryRepository;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.roomPost.repository.RoomPostQueryRepository;
import gaji.service.domain.roomPost.web.dto.RoomPostResponseDto;

import java.util.List;

public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private RoomPostQueryRepository roomPostQueryRepository;
    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts() {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo();
    }
}
