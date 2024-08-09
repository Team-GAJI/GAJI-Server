package gaji.service.domain.common.service;

import gaji.service.domain.common.converter.CategoryConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.repository.CategoryRepository;
import gaji.service.domain.common.web.dto.CategoryResponseDTO;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.global.exception.RestApiException;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
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
    public Category findByCategoryId(Long categoryId) {
        return null;
    }

    @Override
    public boolean existsByCategory(CategoryEnum category) {
        return categoryRepository.existsByCategory(category);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public List<SelectCategory> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType) {
        return selectCategoryRepository.findAllFetchJoinWithCategoryByEntityIdAndPostType(entityId, postType);
    }

    // 카테고리가 존재하면 category에 찾아서 저장, 존재하지 않으면 예외 발생
    @Override
    public List<Category> createCategoryEntityList(List<Long> categoryIdList) {
        return categoryIdList.stream()
                .map(categoryId -> {
                    if (existsByCategoryId(categoryId)) {
                        return findByCategoryId(categoryId);
                    } else {
                        throw new RestApiException(GlobalErrorStatus._INVALID_CATEGORY);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDTO.BaseDTO> findAllCategory() {
        return CategoryConverter.toBaseDTOList(categoryRepository.findAll());
    }

    @Override
    public void saveAllSelectCategory(List<SelectCategory> selectCategoryList) {
        selectCategoryRepository.saveAll(selectCategoryList);
    }
}
