package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.entity.RecruitPost;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;

public interface RecruitCommandService {

    RecruitPost createRecruitPost(RecruitRequestDTO.CreateRecruitDTO request, Long roomId, Long userId);
}
