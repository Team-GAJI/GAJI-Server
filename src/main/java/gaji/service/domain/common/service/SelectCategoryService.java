package gaji.service.domain.common.service;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.PostTypeEnum;

public interface SelectCategoryService {
    void saveSelectCategory(SelectCategory selectCategory);

    SelectCategory findByEntityIdAndType(Long entityId, PostTypeEnum type);
}

