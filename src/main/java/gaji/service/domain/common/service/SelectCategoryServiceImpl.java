package gaji.service.domain.common.service;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.repository.SelectCategoryRepository;
import gaji.service.domain.enums.PostTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectCategoryServiceImpl implements SelectCategoryService {
    private final SelectCategoryRepository selectCategoryRepository;

    @Override
    @Transactional
    public void saveSelectCategory(SelectCategory selectCategory) {
        selectCategoryRepository.save(selectCategory);
    }

    @Override
    public SelectCategory findByEntityIdAndType(Long entityId, PostTypeEnum type) {
        return selectCategoryRepository.findByEntityIdAndType(entityId, type);
    }

}
