package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface StudyCommentQueryService {

    RecruitResponseDTO.CommentListDTO getCommentList
            (Long roomId, Integer lastCommentOrder, Integer lastDepth, Long lastCommentId, int size);
}


