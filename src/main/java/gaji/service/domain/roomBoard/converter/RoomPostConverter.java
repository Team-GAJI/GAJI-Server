package gaji.service.domain.roomBoard.converter;

import gaji.service.domain.roomBoard.entity.RoomBoard;
import gaji.service.domain.roomBoard.entity.RoomInfo.InfoPostComment;
import gaji.service.domain.roomBoard.entity.RoomInfo.RoomInfoPost;
import gaji.service.domain.roomBoard.entity.RoomPost.PostComment;
import gaji.service.domain.roomBoard.entity.RoomPost.RoomPost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.RoomTroublePost;
import gaji.service.domain.roomBoard.entity.RoomTrouble.TroublePostComment;
import gaji.service.domain.roomBoard.web.dto.RoomPostRequestDto;
import gaji.service.domain.roomBoard.web.dto.RoomPostResponseDto;
import gaji.service.domain.studyMate.entity.StudyMate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class RoomPostConverter {

    public static RoomPost toRoomPost(RoomPostRequestDto.RoomPostDto requestDto, StudyMate studyMate, RoomBoard roomBoard) {
        return RoomPost.builder()
                 .studyMate(studyMate)
                 .title(requestDto.getTitle())
                 .body(requestDto.getBody())
                 .roomBoard(roomBoard)
                 .build();
    }

    public static RoomTroublePost toRoomTroublePost(RoomPostRequestDto.RoomTroubloePostDto requestDto, StudyMate studyMate, RoomBoard roomBoard) {
        return RoomTroublePost.builder()
                .studyMate(studyMate)
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .roomBoard(roomBoard)
                .build();
    }

    //트러블 슈팅 게시판 id 반환 dto
    public static RoomPostResponseDto.toCreateRoomTroublePostIdDTO troublePostIdDto(Long id){
        return RoomPostResponseDto.toCreateRoomTroublePostIdDTO.builder()
                .troublePostId(id)
                .build();
    }

    public static RoomPostResponseDto.toCreateRoomPostIdDTO postIdDto(Long id){
        return RoomPostResponseDto.toCreateRoomPostIdDTO.builder()
                .roomPostId(id)
                .build();
    }

    public static RoomInfoPost toRoomInfoPost(RoomPostRequestDto.RoomInfoPostDto requestDto, StudyMate studyMate, RoomBoard roomBoard) {
        return RoomInfoPost.builder()
                .studyMate(studyMate)
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .roomBoard(roomBoard)
                .build();
    }
    public static RoomPostResponseDto.toCreateRoomInfoPostIdDTO infoPostIdDto(Long id){
        return RoomPostResponseDto.toCreateRoomInfoPostIdDTO.builder()
                .infoPostId(id)
                .build();
    }


    public static RoomPostResponseDto.toWriteCommentDto toWriteCommentDto(TroublePostComment newComment) {
        return RoomPostResponseDto.toWriteCommentDto.builder()
                .commentId(newComment.getId())
                .build();
    }

    public static RoomPostResponseDto.toWriteCommentDto toWritePostCommentDto(PostComment newComment) {
        return RoomPostResponseDto.toWriteCommentDto.builder()
                .commentId(newComment.getId())
                .build();
    }
    public static RoomPostResponseDto.toWriteCommentDto toWriteInfoPostCommentDto(InfoPostComment newComment) {
        return RoomPostResponseDto.toWriteCommentDto.builder()
                .commentId(newComment.getId())
                .build();
    }

    public static RoomPostResponseDto.RoomPostDetailDTO toroomPostDetailDTO(RoomPost post, StudyMate studyMate,Page<RoomPostResponseDto.CommentWithRepliesDTO> comments ){
        RoomPostResponseDto.RoomPostDetailDTO dto = new RoomPostResponseDto.RoomPostDetailDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setAuthorName(post.getStudyMate().getUser().getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setViewCount(post.getViewCount());
        dto.setLikeCount(post.getLikeCount());
        dto.setBookmarkCount(post.getBookmarkCount());
        dto.setLiked(post.getRoomPostLikesList().stream()
                .anyMatch(like -> like.getStudyMate().getId().equals(studyMate.getId())));
        dto.setBookmarked(post.getRoomPostBookmarkList().stream()
                .anyMatch(bookmark -> bookmark.getStudyMate().getId().equals(studyMate.getId())));

        dto.setComments(comments);

        return dto;
    }
}
