package gaji.service.domain.user.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum UserErrorStatus implements BaseErrorCodeInterface {
    // 스터디룸 게시판
    _USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_4001", "존재하지 않는 회원입니다."),
    _POST_NOT_CREATED(HttpStatus.BAD_REQUEST, "USER_4002", "게시글이 없습니다."),
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
