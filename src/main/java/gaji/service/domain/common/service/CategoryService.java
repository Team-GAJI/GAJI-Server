package gaji.service.domain.common.service;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category findByCategory(CategoryEnum category);
    boolean existByCategory(CategoryEnum category);
    List<SelectCategory> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType);
    List<Category> createCategoryEntityList(List<CategoryEnum> categoryEnumList);
    void saveAllSelectCategory(List<SelectCategory> selectCategoryList);
}

