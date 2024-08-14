package gaji.service.domain.roomBoard.code;

import gaji.service.global.exception.code.BaseCodeDto;
import gaji.service.global.exception.code.BaseErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum RoomPostErrorStatus implements BaseErrorCodeInterface {
    // 스터디룸 게시판
    _ROOM_BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "ROOM_4001", "존재하지 않는 게시판입니다."),

    _TROUBLE_POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "TROUBLE_4001", "존재하지 않는 게시글입니다."),

    _USER_NOT_DELETE_AUTH(HttpStatus.BAD_REQUEST, "AUTH_4001", "게시글을 삭제할 권한이 없습니다."),
    _USER_NOT_UPDATE_AUTH(HttpStatus.BAD_REQUEST, "AUTH_4001", "게시글을 수정할 권한이 없습니다.");






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
