package gaji.service.domain.recruit.converter;

import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class SortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String param) {
        if (!StringUtils.hasText(param)) throw new RestApiException(RecruitErrorStatus._INVALID_SORT_TYPE);
        return SortType.from(param);
    }
}