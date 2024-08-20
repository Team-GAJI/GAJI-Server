package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface RecruitCommandService {

    RecruitResponseDTO.CreateRoomResponseDTO createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId);

    void deleteStudy(Long userId, Long roomId);

    RecruitResponseDTO.StudyLikesIdResponseDTO likeStudy(Long userId, Long roomId);

    void unLikeStudy(Long userId, Long roomId);

    RecruitResponseDTO.StudyBookmarkIdDTO bookmarkStudy(Long userId, Long roomId);

    void unBookmarkStudy(Long userId, Long roomId);

    RecruitResponseDTO.JoinStudyResponseDTO joinStudy(Long userId, Long roomId);

    void leaveStudy(Long userId, Long roomId);

    void kickStudy(Long userId, Long roomId, Long targetId);
}
