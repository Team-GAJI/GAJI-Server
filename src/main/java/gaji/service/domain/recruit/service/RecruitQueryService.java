package gaji.service.domain.recruit.service;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;

public interface RecruitQueryService {
    RecruitResponseDTO.studyDetailResponseDTO getStudyDetail(Long roomId);

    RecruitResponseDTO.PreviewListResponseDTO getPreviewList(
            Long categoryId, PreviewFilter filter, SortType sort, String query, Long value, int pageSize);

    RecruitResponseDTO.DefaultPreviewListResponseDTO getDefaultPreview(boolean isFirst, int nextCategoryId, int pageSize);
}
