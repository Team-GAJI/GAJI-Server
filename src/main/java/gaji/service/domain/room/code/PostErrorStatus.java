package gaji.service.domain.room.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum PostErrorStatus implements BaseErrorCodeInterface {

    _ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ROOM_4001", "존재하지 않는 스터디룸입니다."),
    _WEEK_NOT_VALID(HttpStatus.BAD_REQUEST, "ROOM_4001", "유효하지 않은 형식의 주차입니다.");

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
