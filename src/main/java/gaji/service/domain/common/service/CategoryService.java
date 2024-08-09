package gaji.service.domain.common.service;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.web.dto.CategoryResponseDTO;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category findByCategory(CategoryEnum category);
    Category findByCategoryId(Long categoryId);
    boolean existsByCategory(CategoryEnum category);
    boolean existsByCategoryId(Long categoryId);
    List<SelectCategory> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType);
    List<Category> createCategoryEntityList(List<Long> categoryIdList);
    List<CategoryResponseDTO.BaseDTO> findAllCategory();
    void saveAllSelectCategory(List<SelectCategory> selectCategoryList);
}

