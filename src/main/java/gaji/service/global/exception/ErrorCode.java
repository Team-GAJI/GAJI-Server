package gaji.service.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorCode {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
