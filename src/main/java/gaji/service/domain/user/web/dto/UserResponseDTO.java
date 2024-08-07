package gaji.service.domain.user.web.dto;

import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetPostDTO {
        Long postId;
        Long userId;
        String nickname;
        String profileImagePath;
        String title;
        String body;
        PostStatusEnum status;
        PostTypeEnum type;
        String createdAt; //DateConverter 이용
        int viewCnt;
        int likeCnt;

    }
}



