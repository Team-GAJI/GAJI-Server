package gaji.service.domain.recruit.converter;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.enums.Role;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.global.converter.DateConverter;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecruitConverter {

    public static Room toRoom(RecruitRequestDTO.CreateRoomDTO request, User user, String thumbnailUrl, String inviteCode, int peopleMaximum) {
        return Room.builder()
                .user(user)
                .name(request.getName())
                .description(request.getDescription())
                .thumbnailUrl(thumbnailUrl)
                .recruitStartDay(request.getRecruitStartDay())
                .recruitEndDay(request.getRecruitEndDay())
                .studyStartDay(request.getStudyStartDay())
                .studyEndDay((request.getStudyEndDay()))
                .isPrivate(request.isPrivate())
                .inviteCode(inviteCode)
                .peopleLimited(request.isPeopleLimited())
                .peopleMaximum(peopleMaximum)
                .build();
    }

    public static RecruitResponseDTO.CreateRoomDTO toResponseDTO(Room room) {
        return RecruitResponseDTO.CreateRoomDTO.builder()
                .roomId(room.getId())
                .build();
    }

    public static List<CategoryEnum> toCategoryList(List<SelectCategory> selectCategoryList) {
        List<CategoryEnum> categoryList = new ArrayList<>();
        for (SelectCategory selectCategory : selectCategoryList) {
            Category category = selectCategory.getCategory();
            categoryList.add(category.getCategory());
        }

        return categoryList;
    }

    public static Material toMaterial(String materialPath, Room room) {
        return Material.builder()
                .room(room)
                .path(materialPath)
                .build();
    }

    public static StudyMate toStudyMate(User user, Room room) {
        return StudyMate.builder()
                .user(user)
                .room(room)
                .role(Role.READER)
                .build();
    }

    public static RecruitResponseDTO.studyDetailDTO toStudyDetailDTO(User user, Room room, List<CategoryEnum> categoryList) {
        return RecruitResponseDTO.studyDetailDTO.builder()
                .userNickName(user.getNickname())
                .userActive(user.getStatus())
                .inactiveTime(user.getInactiveTime())

                .name(room.getName())
                .imageUrl(room.getThumbnailUrl())
                .recruitPostTypeEnum(room.getRecruitPostTypeEnum())
                .postCategoryList(categoryList)
                .views(room.getViews())
                .likes(room.getLikes())
                .bookmarks(room.getBookmarks())

                .recruitStartTime(room.getRecruitStartDay())
                .recruitEndTime(room.getRecruitEndDay())
                .studyStartTime(room.getStudyStartDay())
                .studyEndTime(room.getStudyEndDay())
                .materialList(room.getMaterialList().stream().map(Material::getPath).collect(Collectors.toList()))
                .description(room.getDescription())
                .build();
    }

    private static RecruitResponseDTO.CommentResponseDTO toCommentResponseDTO(StudyComment comment) {
        return RecruitResponseDTO.CommentResponseDTO.builder()
                .userImage(comment.getUser().getProfileImagePth())
                .userNickName(comment.getUser().getNickname())
                .commentOrder(comment.getCommentOrder())
                .depth(comment.getDepth())
                .commentId(comment.getId())
                .commentWriteDate(DateConverter.convertWriteTimeFormat(LocalDate.from(comment.getCreatedAt()), " 작성"))
                .commentBody(comment.getBody())
                .build();
    }

    public static RecruitResponseDTO.CommentListDTO toCommentListDTO(int commentCount, Slice<StudyComment> commentList) {
        List<RecruitResponseDTO.CommentResponseDTO> CommentResponseDTO =
                commentList.stream().map(RecruitConverter::toCommentResponseDTO).collect(Collectors.toList());

        return RecruitResponseDTO.CommentListDTO.builder()
                .commentCount(commentCount)
                .hasNext(commentList.hasNext())
                .commentList(CommentResponseDTO)
                .build();
    }

    public static StudyComment toComment(RecruitRequestDTO.WriteCommentDTO request, User user, Room room, StudyComment parentComment) {
        return StudyComment.builder()
                .user(user)
                .room(room)
                .parentComment(parentComment)
                .body(request.getBody())
                .build();
    }

    public static RecruitResponseDTO.WriteCommentDTO toWriteCommentDTO(StudyComment comment) {
        return RecruitResponseDTO.WriteCommentDTO.builder()
                .commentId(comment.getId())
                .build();
    }
}
