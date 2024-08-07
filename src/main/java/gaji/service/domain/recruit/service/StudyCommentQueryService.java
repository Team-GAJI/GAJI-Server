package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;

import java.util.List;

public interface StudyCommentQueryService {

    RecruitResponseDTO.CommentListDTO getCommentList(Long roomId);
}


