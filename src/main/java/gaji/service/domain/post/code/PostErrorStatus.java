package gaji.service.domain.post.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum PostErrorStatus implements BaseErrorCodeInterface {

    _POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_4001", "존재하지 않는 게시글입니다."),
    _INVALID_POST_TYPE(HttpStatus.BAD_REQUEST, "POST_4002", "유효하지 않은 게시글 유형입니다."),
    _INVALID_POST_STATUS(HttpStatus.BAD_REQUEST, "POST_4003", "유효하지 않은 게시글 상태값입니다."),


    _COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_4004", "존재하지 않는 댓글입니다."),

    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess = false;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDto getErrorCode() {
        return gaji.service.global.exception.code.BaseCodeDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
