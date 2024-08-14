package gaji.service.domain.recruit.service;

import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.repository.StudyCommentRepository;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyCommentCommandServiceImpl implements StudyCommentCommandService{

    private final StudyCommentRepository studyCommentRepository;
    private final UserQueryService userQueryService;
    private final RoomQueryService roomQueryService;

    @Override
    public RecruitResponseDTO.WriteCommentDTO writeComment(Long userId, Long roomId, Long parentCommentId, RecruitRequestDTO.WriteCommentDTO request) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        StudyComment comment = createComment(request, user, room, parentCommentId);
        studyCommentRepository.save(comment);
        room.increaseCommentCount();
        return RecruitConverter.toWriteCommentDTO(comment);
    }

    private StudyComment createComment(
            RecruitRequestDTO.WriteCommentDTO request, User user, Room room, Long parentCommentId) {
        if (parentCommentId != null) {
            StudyComment parentComment = studyCommentRepository.findById(parentCommentId).
                    orElseThrow(()->new RestApiException(RecruitErrorStatus._COMMENT_NOT_FOUND));
            return RecruitConverter.toComment(request, user, room, parentComment);
        } else {
            return RecruitConverter.toComment(request, user, room, null);
        }
    }
}
