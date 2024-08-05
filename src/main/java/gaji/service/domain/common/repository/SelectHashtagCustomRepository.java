package gaji.service.domain.common.repository;

import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;

public interface SelectHashtagCustomRepository {

    List<SelectHashtag> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType);
}
