package gaji.service.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum PostTypeEnum {
    ROOM("스터디"),
    BLOG("블로그"),
    PROJECT_RECRUITMENT("프로젝트 모집"),
    QUESTION("질문");

    @JsonValue
    private final String value;

    PostTypeEnum(String value) {
        this.value = value;
    }

    @JsonCreator // Json -> Object, 역직렬화 수행하는 메서드
    public static PostTypeEnum from(String param) {
        for (PostTypeEnum postTypeEnum : PostTypeEnum.values()) {
            if (postTypeEnum.getValue().equals(param)) {
                return postTypeEnum;
            }
        }
        log.error("PostTypeEnum.from() exception occur param: {}", param);
        return null;
    }

}
