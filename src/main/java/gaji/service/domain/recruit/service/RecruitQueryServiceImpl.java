package gaji.service.domain.recruit.service;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.PreviewFilter;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.repository.RecruitRepository;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final RoomCommandService roomCommandService;
    private final CategoryService categoryService;
    private final RecruitRepository recruitRepository;

    @Override
    @Transactional
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId) {

        Room room = roomQueryService.findRoomById(roomId);
        User user = userQueryService.findUserById(room.getUser().getId());

        room.addView();
        roomCommandService.saveRoom(room);

        SelectCategory selectCategory =
                categoryService.findByEntityIdAndType(room.getId(), PostTypeEnum.ROOM);

        CategoryEnum category = RecruitConverter.toCategory(selectCategory);

        return RecruitConverter.toStudyDetailDTO(user, room, category);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruitResponseDTO.PreviewListDTO getPreviewList(
            CategoryEnum category, PreviewFilter filter, SortType sort, String query, Long value, int pageSize) {

        validateQuery(query);

        Pageable pageable = PageRequest.of(0, pageSize);

        return recruitRepository.findByCategoryOrderBySortType(category, filter, sort, query, value, pageable);
    }

    @Override
    public RecruitResponseDTO.DefaultPreviewListDTO getDefaultPreview(boolean isFirst, Integer nextCategoryIndex, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        List<RecruitResponseDTO.DefaultPreviewDTO> defaultPreviewList = new ArrayList<>();

        List<CategoryEnum> categoryList = new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        int count;

        if (isFirst) {
            nextCategoryIndex = 0;
            count = 4;
        } else {
            count = nextCategoryIndex + 1;
        }

        for (int i = nextCategoryIndex; i < count; i++) {
            CategoryEnum category = categoryList.get(i);

            RecruitResponseDTO.DefaultPreviewDTO previewList =
                    recruitRepository.findByCategory(category, pageable);

            if (previewList.getPreviewList() == null || previewList.getPreviewList().isEmpty()) {
                continue;
            }

            defaultPreviewList.add(previewList);
        }

        return RecruitResponseDTO.DefaultPreviewListDTO.builder()
                .defaultPreviewList(defaultPreviewList)
                .nextIndex(count)
                .build();
    }

    private void validateQuery(String query) {
        if (query == null) {
            return; // query가 null이면 검사를 하지 않음.
        }

        int queryLength = query.length();

        if (queryLength < 2) {
            throw new RestApiException(RecruitErrorStatus._QUERY_SO_SHORT);
        }
        if (queryLength > 20) {
            throw new RestApiException(RecruitErrorStatus._QUERY_SO_LONG);
        }
    }
}
