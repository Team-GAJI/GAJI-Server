package gaji.service.domain.recruit.converter;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.recruit.entity.RecruitPostBookmark;
import gaji.service.domain.recruit.entity.RecruitPostLikes;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.enums.Role;
import gaji.service.domain.recruit.entity.StudyComment;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.global.converter.DateConverter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
                .commentCreatedAt(comment.getCreatedAt())
                .commentBody(comment.getBody())
                .build();
    }

    public static List<RecruitResponseDTO.CommentResponseDTO> toCommentResponseDTOList(List<StudyComment> commentList) {
        int toIndex = Math.min(4, commentList.size());
        return commentList.subList(0, toIndex).stream().map(RecruitConverter::toCommentResponseDTO).collect(Collectors.toList());
    }

    public static RecruitResponseDTO.CommentListDTO toCommentListDTO(int commentCount, List<RecruitResponseDTO.CommentResponseDTO> CommentResponseDTO) {
        return RecruitResponseDTO.CommentListDTO.builder()
                .commentCount(commentCount)
                .commentList(CommentResponseDTO)
                .build();
    }

    public static RecruitPostLikes toRecruitPostLikes(User user, Room room) {
        return RecruitPostLikes.builder()
                .user(user)
                .room(room)
                .build();
    }

    public static RecruitResponseDTO.StudyLikesIdDTO toStudyLikesIdDTO(RecruitPostLikes likes) {
        return RecruitResponseDTO.StudyLikesIdDTO.builder()
                .studyLikesId(likes.getId())
                .build();
    }

    public static RecruitPostBookmark toRecruitPostBookmark(User user, Room room) {
        return RecruitPostBookmark.builder()
                .user(user)
                .room(room)
                .build();
    }

    public static RecruitResponseDTO.StudyBookmarkIdDTO toStudyBookmarkIdDTO(RecruitPostBookmark bookmark) {
        return RecruitResponseDTO.StudyBookmarkIdDTO.builder()
                .studyBookmarkId(bookmark.getId())
                .build();
    }

    public static RecruitResponseDTO.PreviewDTO toPreviewDTO(Room room) {
        return RecruitResponseDTO.PreviewDTO.builder()
                .imageUrl(room.getThumbnailUrl())
                .recruitStatus(room.getRecruitPostTypeEnum())
                .applicant(room.getStudyApplicantList().size())
                .name(room.getName())
                .deadLine(ChronoUnit.DAYS.between(room.getRecruitEndDay(), LocalDate.now()))
                .description(room.getDescription())
                .createdAt(DateConverter.convertToRelativeTimeFormat(room.getCreatedAt()))
                .recruitCount(room.getPeopleMaximum())
                .build();
    }

    public static List<RecruitResponseDTO.PreviewDTO> toPreviewDTOLIST(List<Room> roomList) {
        return roomList.stream().map(RecruitConverter::toPreviewDTO).collect(Collectors.toList());
    }
}
