package gaji.service.domain.roomBoard.converter;

import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import org.springframework.data.domain.Page;

public class RoomInfoPostConverter {
    public static RoomPostResponseDto.RoomInfoPostDetailDTO toRoomInfoPostDetailDTO(RoomInfoPost post, StudyMate studyMate, Page<RoomPostResponseDto.CommentWithRepliesDTO> comments ){
        RoomPostResponseDto.RoomInfoPostDetailDTO dto = new RoomPostResponseDto.RoomInfoPostDetailDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setAuthorName(post.getStudyMate().getUser().getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setViewCount(post.getViewCount());
        dto.setLikeCount(post.getLikeCount());
        dto.setBookmarkCount(post.getBookmarkCount());
        dto.setLiked(post.getRoomInfoPostLikesList().stream()
                .anyMatch(like -> like.getStudyMate().getId().equals(studyMate.getId())));
        dto.setBookmarked(post.getRoomInfoPostBookmarkList().stream()
                .anyMatch(bookmark -> bookmark.getStudyMate().getId().equals(studyMate.getId())));

        dto.setComments(comments);

        return dto;
    }

}
