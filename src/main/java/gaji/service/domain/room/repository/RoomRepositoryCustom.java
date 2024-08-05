package gaji.service.domain.room.repository;

import gaji.service.domain.room.web.dto.RoomResponseDto;

public interface RoomRepositoryCustom {
    RoomResponseDto.StudyRoomInfoDTO getStudyRoomInfo(Long roomId);

}
