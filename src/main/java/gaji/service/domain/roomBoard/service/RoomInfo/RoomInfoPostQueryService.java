package gaji.service.domain.roomBoard.service.RoomInfo;

import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;

public interface RoomInfoPostQueryService {
    RoomInfoPost findInfoPostById(Long PostId);
}
