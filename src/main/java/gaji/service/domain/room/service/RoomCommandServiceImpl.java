package gaji.service.domain.room.service;

import gaji.service.domain.curriculum.entity.Curriculum;
import gaji.service.domain.curriculum.repository.CurriculumRepository;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.Way;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.repository.WayRepository;
import gaji.service.domain.room.web.dto.RoomRequestDTO;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomCommandServiceImpl implements RoomCommandService {
    private final RoomRepository roomRepository;
    private final CurriculumRepository curriculumRepository;
    private final WayRepository wayRepository;
    private static final String DEFAULT_THUMBNAIL_URL = "https://gaji-bucket.s3.ap-northeast-2.amazonaws.com/study/gaji.png";

    @Override
    @Transactional
    public Room createStudy(RoomRequestDTO.CreateStudyDTO request){
        String thumbnailPath = null;
        List<Material> materials = null;
        Curriculum curriculum = null;
        Way way = null;

        if (request.getCurriculumId() != null) {
            curriculum = curriculumRepository.findById(request.getCurriculumId())
                    .orElseThrow(() -> new RestApiException(RoomErrorStatus._CURRICULUM_NOT_FOUND)); // 커리큘럼이 없을 경우 null로 설정
        }

        if (request.getWayId() != null) {
            way = wayRepository.findById(request.getWayId())
                    .orElseThrow(() -> new RestApiException(RoomErrorStatus._WAY_NOT_FOUND)); // 진행방식이 없을 경우 null로 설정
        }

        Room room = RoomConverter.toRoom(request, thumbnailPath, materials, curriculum, way);

        roomRepository.save(room);
        return room;
    }


}
