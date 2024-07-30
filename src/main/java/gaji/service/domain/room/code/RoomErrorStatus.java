package gaji.service.domain.room.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum RoomErrorStatus implements BaseErrorCodeInterface {

    _CURRICULUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "CURRICULUM_4001", "존재하지 않는 커리큘럼입니다."),
    _WAY_NOT_FOUND(HttpStatus.BAD_REQUEST, "WAY_4001", "존재하지 않는 진행방식입니다.");

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
