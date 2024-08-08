package gaji.service.domain.post.converter;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.web.dto.CommentResponseDTO;
import gaji.service.global.converter.DateConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static CommentResponseDTO.PostCommentDTO toPostCommentDTO(Comment comment) {
        return CommentResponseDTO.PostCommentDTO.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getName())
                .body(comment.getBody())
                .groupNum(comment.getGroupNum())
                .depth(comment.getDepth())
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(comment.getCreatedAt()), "작성"))
                .build();
    }

    public static CommentResponseDTO.PostCommentListDTO toPostCommentListDTO(List<Comment> commentList, boolean hasNext) {
        List<CommentResponseDTO.PostCommentDTO> postCommentDTOList = commentList.stream()
                .map(CommentConverter::toPostCommentDTO)
                .collect(Collectors.toList());

        return CommentResponseDTO.PostCommentListDTO.builder()
                .commentList(postCommentDTOList)
                .hasNext(hasNext)
                .build();
    }
}
