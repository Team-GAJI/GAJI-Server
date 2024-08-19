package gaji.service.domain.post.converter;

import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.service.CommunityCommentService;
import gaji.service.domain.post.web.dto.CommunityPostCommentResponseDTO;
import gaji.service.global.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CommunityCommentConverter {
    private final CommunityCommentService communityCommentService;

    public static CommunityPostCommentResponseDTO.WriteCommentDTO toWriteCommentDTO(CommunityComment comment) {
        return CommunityPostCommentResponseDTO.WriteCommentDTO.builder()
                .commentId(comment.getId())
                .build();
    }

    public static CommunityPostCommentResponseDTO.PostCommentDTO toPostCommentDTO(CommunityComment comment, boolean isWriter) {
        return CommunityPostCommentResponseDTO.PostCommentDTO.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getName())
                .body(comment.getBody())
                .groupNum(comment.getGroupNum())
                .depth(comment.getDepth())
                .isWriter(isWriter)
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(comment.getCreatedAt()), " 작성"))
                .build();
    }

    public CommunityPostCommentResponseDTO.PostCommentListDTO toPostCommentListDTO(List<CommunityComment> commentList, boolean hasNext, Long userId) {
        List<CommunityPostCommentResponseDTO.PostCommentDTO> postCommentDTOList = new ArrayList<>();

        for (CommunityComment communityComment : commentList) {
            boolean isWriter = (userId == null) ? false : communityCommentService.isCommentWriter(userId, communityComment);
            postCommentDTOList.add(CommunityCommentConverter.toPostCommentDTO(communityComment, isWriter));
        }

        return CommunityPostCommentResponseDTO.PostCommentListDTO.builder()
                .commentList(postCommentDTOList)
                .hasNext(hasNext)
                .build();
    }
}
