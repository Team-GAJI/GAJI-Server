package gaji.service.domain.recruit.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.RoomCategoryEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.repository.StudyCommentRepository;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final StudyCommentRepository studyCommentRepository;

    @Override
    @Transactional(readOnly = true)
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._RECRUIT_POST_NOT_FOUND));

        User user = userRepository.findById(room.getUser().getId())
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._USER_NOT_FOUND));

        List<StudyComment> commentList = studyCommentRepository.findByRoomAndDepth(room, 0);
        List<RecruitResponseDTO.CommentResponseDTO> CommentResponseDTO;

        if (commentList.isEmpty()) {
            CommentResponseDTO = null;
        } else {
            commentList.sort(Comparator.comparing(StudyComment::getCreatedAt).reversed());
            CommentResponseDTO = RecruitConverter.toCommentResponseDTOList(commentList);
        }


        room.addView();
        roomRepository.save(room);

        List<RoomCategoryEnum> categoryList = RecruitConverter.toCategoryList(room.getSelectCategoryList());

        return RecruitConverter.toStudyDetailDTO(user, room, categoryList, commentList.size(), CommentResponseDTO);
    }


}
