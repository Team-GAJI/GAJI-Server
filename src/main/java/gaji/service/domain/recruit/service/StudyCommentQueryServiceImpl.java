package gaji.service.domain.recruit.service;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.repository.StudyCommentRepository;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
    public RecruitResponseDTO.CommentListDTO getCommentList(Long roomId, Integer lastCommentOrder, int size) {
        Room room = roomQueryService.findRoomById(roomId);
        PageRequest pageRequest = PageRequest.of(0, size);

        Slice<StudyComment> studyCommentList =
                studyCommentRepository.findByRoomFetchJoinWithUser(lastCommentOrder, room, pageRequest);

        return RecruitConverter.toCommentListDTO(room.getCommentCount(), studyCommentList);
    }
}
