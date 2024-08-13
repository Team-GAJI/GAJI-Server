package gaji.service.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import gaji.service.global.exception.RestApiException;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum CategoryEnum {
    DEVELOP("개발"),
    AI("인공지능"),
    HW("하드웨어"),
    SECURITY("보안"),
    NETWORK("클라우드 네트워크"),
    LANGUAGE("어학"),
    DESIGN("디자인"),
    BUSINESS("비즈니스"),
    BOOK("독서 모임");

    @JsonValue
    private final String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    @JsonCreator // Json -> Object, 역직렬화 수행하는 메서드
    public static CategoryEnum from(String param) {
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            if (categoryEnum.getValue().equals(param)) {
                return categoryEnum;
            }
        }
        log.error("CategoryEnum.from() exception occur param: {}", param);
        throw new RestApiException(GlobalErrorStatus._INVALID_CATEGORY);
    }
}
