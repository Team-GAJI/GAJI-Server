package gaji.service.domain.user.converter;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserResponseDTO.GetRoomDTO toGetRoomDTO(Room room) {
        return UserResponseDTO.GetRoomDTO.builder()
                .roomId(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .thumbnail_url(room.getThumbnailUrl())
                .isOngoing(room.getStudyEndDay().isAfter(LocalDate.now())) //스터디 종료 날짜가 현재날짜보다 뒤라면 true
                .build();
    }

    public static UserResponseDTO.GetRoomListDTO toGetRoomListDTO(List<Room> roomList) {
        List<UserResponseDTO.GetRoomDTO> GetRoomDTOList = roomList.stream()
                .map(UserConverter::toGetRoomDTO).toList();

        return UserResponseDTO.GetRoomListDTO.builder()
                .roomList(GetRoomDTOList)
                .totalElements(GetRoomDTOList.size())
                .build();
    }
}
