package gaji.service.domain.recruit.service;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;
    private final RoomRepository roomRepository;
    private final StudyCommentQueryService studyCommentQueryService;
    private final SelectCategoryRepository selectCategoryRepository;

    @Override
    @Transactional
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId) {

        Room room = roomQueryService.findRoomById(roomId);
        User user = userQueryService.findUserById(room.getUser().getId());

        List<StudyComment> commentList = studyCommentQueryService.findByRoomAndDepth(room, 0);
        List<RecruitResponseDTO.CommentResponseDTO> CommentResponseDTO;

        int commentCount = studyCommentQueryService.getCommentCountByRoom(room);

        if (commentList.isEmpty()) {
            CommentResponseDTO = null;
        } else {
            commentList.sort(Comparator.comparing(StudyComment::getCreatedAt).reversed());
            CommentResponseDTO = RecruitConverter.toCommentResponseDTOList(commentList);
        }

        room.addView();
        roomRepository.save(room);

        List<SelectCategory> selectCategoryList =
                selectCategoryRepository.findAllByEntityIdAndType(room.getId(), PostTypeEnum.ROOM);


        List<CategoryEnum> categoryList = RecruitConverter.toCategoryList(selectCategoryList);

        return RecruitConverter.toStudyDetailDTO(user, room, categoryList, commentCount, CommentResponseDTO);
    }


}
