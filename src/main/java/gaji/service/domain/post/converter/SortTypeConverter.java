package gaji.service.domain.post.converter;

import gaji.service.domain.enums.SortType;
import gaji.service.global.exception.RestApiException;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class SortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String param) {
        return SortType.from(param);
    }
}
