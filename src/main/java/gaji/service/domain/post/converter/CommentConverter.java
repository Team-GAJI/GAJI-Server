package gaji.service.domain.post.converter;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.web.dto.CommentResponseDTO;

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
                .orderNum(comment.getOrderNum())
                .depth(comment.getDepth())
                .createdAt(LocalDate.from(comment.getCreatedAt()))
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
