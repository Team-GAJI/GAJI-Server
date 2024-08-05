package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class PostTypeConverter implements Converter<String, PostTypeEnum> {

    @Override
    public PostTypeEnum convert(String param) {
        if (!StringUtils.hasText(param)) throw new RestApiException(PostErrorStatus._POST_TYPE_NOT_FOUND);
        return PostTypeEnum.from(param);
    }
}
