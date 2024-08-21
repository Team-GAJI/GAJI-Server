package gaji.service.domain.post.converter;

import gaji.service.domain.enums.PostTypeEnum;
import org.springframework.core.convert.converter.Converter;

public class PostTypeConverter implements Converter<String, PostTypeEnum> {

    @Override
    public PostTypeEnum convert(String param) {
        return PostTypeEnum.from(param);
    }
}
