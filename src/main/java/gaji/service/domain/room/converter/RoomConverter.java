package gaji.service.domain.room.converter;

import gaji.service.domain.curriculum.entity.Curriculum;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.Way;
import gaji.service.domain.room.web.dto.RoomRequestDTO;
import gaji.service.domain.room.web.dto.RoomResponseDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoomConverter {

    public static Room toRoom(RoomRequestDTO.CreateStudyDTO request, String ThumbnailPath, Curriculum curriculum, Way way) {

        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .thumbnailPath(ThumbnailPath)
                .headCount(request.getHeadCount())
                .isPrivate(request.isPrivate())
                .recruitStartDay(request.getRecruitStartDay())
                .recruitEndDay(request.getRecruitEndDay())
                .studyStartDay(request.getStudyStartDay())
                .studyEndDay(request.getStudyEndDay())
                .curriculum(curriculum)
                .way(way)
                .build();
    }

    public static RoomResponseDTO.CreateStudyDTO toResponseDTO(Room room) {
        return RoomResponseDTO.CreateStudyDTO.builder()
                .roomId(room.getId())
                .build();
    }

    public static Material toMaterial(String materialPath, Room room) {

        return Material.builder()
                .room(room)
                .path(materialPath)
                .build();

    }
}
