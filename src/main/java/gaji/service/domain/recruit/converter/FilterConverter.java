package gaji.service.domain.recruit.converter;

import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class FilterConverter implements Converter<String, PreviewFilter> {

    @Override
    public PreviewFilter convert(String param) {
        if (!StringUtils.hasText(param)) throw new RestApiException(RecruitErrorStatus._INVALID_FILTER);
        return PreviewFilter.from(param);
    }
}