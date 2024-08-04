package gaji.service.domain.user.web.dto;

import gaji.service.domain.enums.UserActive;
import gaji.service.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CancleResultDTO {
        Long userId;
        UserActive userActive;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetPostsResultDTO {
        Long userId;
        List<Long> postIds;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateNicknameResultDTO {
        Long userId;
        String nickname;
    }
}



