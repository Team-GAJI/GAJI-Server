package gaji.service.domain.post.converter;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.web.dto.CommunityPostCommentResponseDTO;
import gaji.service.global.converter.DateConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static CommunityPostCommentResponseDTO.WriteCommentDTO toWriteCommentDTO(Comment comment) {
        return CommunityPostCommentResponseDTO.WriteCommentDTO.builder()
                .commentId(comment.getId())
                .build();
    }

    public static CommunityPostCommentResponseDTO.PostCommentDTO toPostCommentDTO(Comment comment) {
        return CommunityPostCommentResponseDTO.PostCommentDTO.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getName())
                .body(comment.getBody())
                .groupNum(comment.getGroupNum())
                .depth(comment.getDepth())
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(comment.getCreatedAt()), " 작성"))
                .build();
    }

    public static CommunityPostCommentResponseDTO.PostCommentListDTO toPostCommentListDTO(List<Comment> commentList, boolean hasNext) {
        List<CommunityPostCommentResponseDTO.PostCommentDTO> postCommentDTOList = commentList.stream()
                .map(CommentConverter::toPostCommentDTO)
                .collect(Collectors.toList());

        return CommunityPostCommentResponseDTO.PostCommentListDTO.builder()
                .commentList(postCommentDTOList)
                .hasNext(hasNext)
                .build();
    }
}
