package gaji.service.domain.recruit.service;

import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.repository.StudyCommentRepository;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyCommentQueryServiceImpl implements StudyCommentQueryService{

    private final StudyCommentRepository studyCommentRepository;
    private final RoomQueryService roomQueryService;

    @Override
    public RecruitResponseDTO.CommentListDTO getCommentList(Long roomId) {
        Room room = roomQueryService.findRoomById(roomId);

        List<StudyComment> commentList = studyCommentRepository.findByRoomAndDepth(room, 0);
        List<RecruitResponseDTO.CommentResponseDTO> CommentResponseDTO;

        int commentCount = studyCommentRepository.countByRoom(room);

        if (commentList.isEmpty()) {
            CommentResponseDTO = null;
        } else {
            commentList.sort(Comparator.comparing(StudyComment::getCreatedAt).reversed());
            CommentResponseDTO = RecruitConverter.toCommentResponseDTOList(commentList);
        }

        return RecruitConverter.toCommentListDTO(commentCount, CommentResponseDTO);
    }
}
