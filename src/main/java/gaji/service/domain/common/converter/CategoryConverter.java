package gaji.service.domain.common.converter;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static Category toCategory(CategoryEnum category) {
        return Category.builder()
                .category(category)
                .build();
    }

    public static List<SelectCategory> toSelectCategoryList(List<Category> categoryList, Long entityId, PostTypeEnum type) {
        return categoryList.stream()
                .map(category ->
                        SelectCategory.builder()
                                .category(category)
                                .entityId(entityId)
                                .type(type)
                                .build()
                ).collect(Collectors.toList());
    }
}
