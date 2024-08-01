package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.room.entity.Room;

public interface RecruitCommandService {

    Room createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId);
}
