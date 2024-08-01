package gaji.service.domain.recruit.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum RecruitErrorStatus implements BaseErrorCodeInterface {
    _USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_4001","사용자를 찾을 수 없습니다."), // 임시 생성
    _RECRUIT_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "RECRUIT_4001", "해당 카테고리가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess = false;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDto getErrorCode() {
        return BaseCodeDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
