package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.post.code.CommunityPostErrorStatus;
import gaji.service.global.exception.RestApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class PostStatusConverter implements Converter<String, PostStatusEnum> {

    @Override
    public PostStatusEnum convert(String param) {
        return PostStatusEnum.from(param);
    }
}
