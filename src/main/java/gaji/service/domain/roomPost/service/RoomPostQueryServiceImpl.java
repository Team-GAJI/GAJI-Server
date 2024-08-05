package gaji.service.domain.roomPost.service;

import gaji.service.domain.roomPost.repository.RoomPostQueryRepository;
import gaji.service.domain.roomPost.web.dto.RoomPostResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private RoomPostQueryRepository roomPostQueryRepository;

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId) {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo(roomId);
    }

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts() {
        return List.of();
    }
}
