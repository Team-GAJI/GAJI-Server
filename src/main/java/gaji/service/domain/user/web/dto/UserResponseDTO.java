package gaji.service.domain.user.web.dto;

import gaji.service.domain.enums.UserActive;
import gaji.service.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRoomDTO {
        Long roomId;
        String name;
        String description;
        String thumbnail_url;
        LocalDate studyStartDay;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRoomListDTO {
        List<GetRoomDTO> roomList;
        boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserDetailDTO {
        Long userId;
        String nickname;
        String profileImagePth;
    }
}



