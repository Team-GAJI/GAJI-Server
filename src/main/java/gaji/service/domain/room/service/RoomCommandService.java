package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.web.dto.RoomRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomCommandService {
    Room createStudy(RoomRequestDTO.CreateStudyDTO request);
}
