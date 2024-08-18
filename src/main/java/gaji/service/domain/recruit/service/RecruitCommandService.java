package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface RecruitCommandService {

    RecruitResponseDTO.CreateRoomResponseDTO createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId);

    RecruitResponseDTO.StudyLikesIdResponseDTO likeStudy(Long userId, Long roomId);

    void unLikeStudy(Long userId, Long roomId);

    RecruitResponseDTO.StudyBookmarkIdDTO bookmarkStudy(Long userId, Long roomId);

    void unBookmarkStudy(Long userId, Long roomId);
}
