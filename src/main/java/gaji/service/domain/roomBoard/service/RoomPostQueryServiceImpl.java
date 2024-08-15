package gaji.service.domain.roomBoard.service;

import gaji.service.domain.roomBoard.repository.RoomPostQueryRepository;
import gaji.service.domain.roomBoard.repository.RoomTroublePostRepository;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomPostQueryServiceImpl implements RoomPostQueryService {

    private final RoomPostQueryRepository roomPostQueryRepository;
    private final RoomTroublePostRepository roomTroublePostRepository;

    @Override
    public List<RoomPostResponseDto.PostListDto> getTop3RecentPosts(Long roomId) {
        return roomPostQueryRepository.findTop3RecentPostsWithUserInfo(roomId);
    }

    @Override
    public List<RoomPostResponseDto.TroublePostSummaryDto> getPaginatedTroublePosts(Long boardId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return roomTroublePostRepository.findTroublePostSummaries(boardId, (Pageable) pageRequest);
    }

}
