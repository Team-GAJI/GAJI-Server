package gaji.service.domain.recruit.converter;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class CategoryConverter implements Converter<String, CategoryEnum> {

    @Override
    public CategoryEnum convert(String param) {
        if (!StringUtils.hasText(param)) throw new RestApiException(RecruitErrorStatus._INVALID_CATEGORY);
        return CategoryEnum.from(param);
    }
}