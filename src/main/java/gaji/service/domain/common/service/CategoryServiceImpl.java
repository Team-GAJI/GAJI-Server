package gaji.service.domain.common.service;

import gaji.service.domain.common.converter.CategoryConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.repository.CategoryRepository;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SelectCategoryRepository selectCategoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByCategory(CategoryEnum category) {
        return categoryRepository.findByCategory(category);
    }

    @Override
    public boolean existByCategory(CategoryEnum category) {
        return categoryRepository.existsByCategory(category);
    }

    @Override
    public List<SelectCategory> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType) {
        return selectCategoryRepository.findAllFetchJoinWithCategoryByEntityIdAndPostType(entityId, postType);
    }

    @Override
    public List<Category> createCategoryEntityList(List<CategoryEnum> categoryEnumList) {
        return categoryEnumList.stream()
                .map(category -> {
                    if (existByCategory(category)) {
                        return findByCategory(category);
                    } else {
                        return saveCategory(CategoryConverter.toCategory(category));
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveAllSelectCategory(List<SelectCategory> selectCategoryList) {
        selectCategoryRepository.saveAll(selectCategoryList);
    }
}
