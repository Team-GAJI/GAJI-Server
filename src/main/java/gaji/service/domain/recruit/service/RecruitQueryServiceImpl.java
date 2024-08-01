package gaji.service.domain.recruit.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._RECRUIT_POST_NOT_FOUND));

        User user = userRepository.findById(room.getUser().getId())
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._USER_NOT_FOUND));

        room.addView();
        System.out.println(room.getViews());
        roomRepository.save(room);

        List<RecruitPostCategoryEnum> categoryList = RecruitConverter.toCategoryList(room.getSelectCategoryList());

        return RecruitConverter.toStudyDetailDTO(user, room, categoryList);
    }

}
