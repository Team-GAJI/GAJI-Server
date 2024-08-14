package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface StudyCommentCommandService {

    RecruitResponseDTO.WriteCommentDTO writeComment(
            Long userId, Long roomId, Long parentCommentId, RecruitRequestDTO.WriteCommentDTO request);
}


