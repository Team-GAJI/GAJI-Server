package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.code.RoomPostErrorStatus;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.repository.RoomInfo.RoomInfoPostRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomInfoPostQueryServiceImpl implements RoomInfoPostQueryService{

    private final RoomInfoPostRepository roomInfoPostRepository;

    @Override
    public RoomInfoPost findInfoPostById(Long PostId){
        return roomInfoPostRepository.findById(PostId)
                .orElseThrow(() ->new RestApiException( RoomPostErrorStatus._POST_NOT_FOUND));
    }
}
