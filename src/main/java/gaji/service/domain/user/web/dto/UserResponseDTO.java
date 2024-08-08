package gaji.service.domain.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRoomDTO {
        Long roomId;
        String name;
        String description;
        String thumbnail_url;
        Boolean isOngoing;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRoomListDTO {
        List<GetRoomDTO> roomList;
        Integer totalElements;
    }
}



