package gaji.service.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FIleCategory {
    PROFILE("profile"), BLOG("blog"), PROJECT("project"), QUESTION("question"), STUDY("study"), ETC("etc");
    private final String category;
}
