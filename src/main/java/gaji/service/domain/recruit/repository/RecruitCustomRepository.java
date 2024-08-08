package gaji.service.domain.recruit.repository;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RecruitCustomRepository {

    RecruitResponseDTO.PreviewListDTO findByCategoryOrderBySortType(
            CategoryEnum category, PreviewFilter filter, SortType sortType, Long value, Pageable pageable);
}
