package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface RecruitQueryService {
    RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId);
}
