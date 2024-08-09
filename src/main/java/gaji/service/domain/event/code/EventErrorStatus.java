package gaji.service.domain.event.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventErrorStatus implements BaseErrorCodeInterface {

    _EVENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "EVENT_4001", "존재하지 않는 이벤트입니다."),
    _EVENT_NOT_MY_EVENT(HttpStatus.BAD_REQUEST, "EVENT_4002", "본인의 이벤트가 아닙니다."),
    _EVENT_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "EVENT_4003", "이미 완료한 이벤트입니다."),
    _EVENT_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "EVENT_4004", "완료하지 않은 이벤트입니다."),
    _EVENT_OVER_LIMIT(HttpStatus.BAD_REQUEST, "EVENT_4005", "하루에 등록 가능한 이벤트는 20개입니다."),

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