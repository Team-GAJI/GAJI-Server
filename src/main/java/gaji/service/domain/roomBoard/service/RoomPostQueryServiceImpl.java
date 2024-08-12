package gaji.service.domain.roomBoard.service;

import gaji.service.domain.roomBoard.repository.RoomPostQueryRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
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
