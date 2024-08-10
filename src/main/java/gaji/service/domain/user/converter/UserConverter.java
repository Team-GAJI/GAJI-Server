package gaji.service.domain.user.converter;

import com.querydsl.core.Tuple;
import gaji.service.domain.room.entity.QRoom;
import gaji.service.domain.user.web.dto.UserResponseDTO;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserResponseDTO.GetRoomDTO toGetRoomDTO(Tuple tuple) {
        return UserResponseDTO.GetRoomDTO.builder()
                .roomId(tuple.get(QRoom.room.id))
                .name(tuple.get(QRoom.room.name))
                .description(tuple.get(QRoom.room.description))
                .thumbnail_url(tuple.get(QRoom.room.thumbnailUrl))
                .studyStartDay(tuple.get(QRoom.room.studyStartDay))
                .build();
    }

    public static UserResponseDTO.GetRoomListDTO toGetRoomListDTO(Slice<Tuple> roomList) {
        List<UserResponseDTO.GetRoomDTO> getRoomDTOList = roomList.stream()
                .map(UserConverter::toGetRoomDTO)
                .collect(Collectors.toList());

        return UserResponseDTO.GetRoomListDTO.builder()
                .roomList(getRoomDTOList)
                .hasNext(roomList.hasNext())
                .build();
    }
}
