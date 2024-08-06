package gaji.service.domain.roomPost.service;

import gaji.service.domain.roomPost.repository.RoomPostQueryRepository;
import gaji.service.domain.roomPost.web.dto.RoomPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private final RoomPostQueryRepository roomPostQueryRepository;

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId) {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo(roomId);
    }

}
