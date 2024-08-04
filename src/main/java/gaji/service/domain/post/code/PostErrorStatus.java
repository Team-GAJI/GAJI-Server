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
    _POST_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_4002", "존재하지 않는 게시글 유형입니다."),
    _HASHTAG_ISBLANK(HttpStatus.BAD_REQUEST, "POST_4003", "공백은 해시태그로 등록할 수 없습니다."),

    _COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_4004", "존재하지 않는 댓글입니다."),
    _USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_4001", "존재하지 않는 회원입니다."), // user 도메인을 건드리지 않기 위해 생성한 임시 에러코드

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
