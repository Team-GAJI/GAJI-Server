package gaji.service.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    DEVELOPMENT("개발"), AI("인공지능"), HARDWORE("하드웨어"), SECURITY("보안")
    , NETWORK_CLOUD("네트워크-클라우드"), LANGUAGE("어학"), DESIGN("디자인")
    , PM("비즈니스-PM"), BOOK("독서"), ETC("기타");
    private final String category;
}
