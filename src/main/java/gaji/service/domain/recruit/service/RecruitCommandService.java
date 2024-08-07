package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;

public interface RecruitCommandService {

    RecruitResponseDTO.CreateRoomDTO createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId);
}
