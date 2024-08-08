package gaji.service.domain.recruit.service;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.repository.RecruitRepository;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Lint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final RoomCommandService roomCommandService;
    private final SelectCategoryRepository selectCategoryRepository;
    private final RecruitRepository recruitRepository;

    @Override
    @Transactional
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId) {

        Room room = roomQueryService.findRoomById(roomId);
        User user = userQueryService.findUserById(room.getUser().getId());

        room.addView();
        roomCommandService.saveRoom(room);

        List<SelectCategory> selectCategoryList =
                selectCategoryRepository.findAllByEntityIdAndType(room.getId(), PostTypeEnum.ROOM);


        List<CategoryEnum> categoryList = RecruitConverter.toCategoryList(selectCategoryList);

        return RecruitConverter.toStudyDetailDTO(user, room, categoryList);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruitResponseDTO.PreviewListDTO getPreviewList(
            CategoryEnum category, PreviewFilter filter, SortType sort, Long value, int pageSize) {

        Pageable pageable = PageRequest.of(0, pageSize);

        RecruitResponseDTO.PreviewListDTO previewList =
                recruitRepository.findByCategoryOrderBySortType(category, filter, sort, value, pageable);

        return previewList;
    }
}
