package gaji.service.domain.recruit.service;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface RecruitQueryService {
    RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId);

    RecruitResponseDTO.PreviewListDTO getPreviewList(
            CategoryEnum category, PreviewFilter filter, SortType sort, Long value, int pageSize);
}
