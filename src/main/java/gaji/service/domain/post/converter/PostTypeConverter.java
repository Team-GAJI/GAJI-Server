package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.code.CommunityPostErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class PostTypeConverter implements Converter<String, PostTypeEnum> {

    @Override
    public PostTypeEnum convert(String param) {
        if (!StringUtils.hasText(param)) throw new RestApiException(CommunityPostErrorStatus._INVALID_POST_TYPE);
        return PostTypeEnum.from(param);
    }
}
