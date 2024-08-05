package gaji.service.domain.common.service;


import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;

public interface HashtagQueryService {

    boolean ExistHashtagByName(String name);
    List<SelectHashtag> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType);
}
