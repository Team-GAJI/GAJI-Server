package gaji.service.domain.recruit.converter;

import gaji.service.domain.User;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.entity.RecruitPost;
import gaji.service.domain.recruit.entity.SelectCategory;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class RecruitConverter {

    public static RecruitPost toRecruitPost(RecruitRequestDTO.CreateRecruitDTO request, User user, Room room, String inviteCode, int peopleMaximum) {
        return RecruitPost.builder()
                .user(user)
                .room(room)
                .title(request.getTitle())
                .content(request.getContent())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isPrivate(request.isPrivate())
                .inviteCode(inviteCode)
                .peopleLimited(request.isPeopleLimited())
                .peopleMaximum(peopleMaximum)
                .build();
    }

    public static RecruitResponseDTO.CreateRecruitDTO toResponseDTO(RecruitPost recruitPost) {
        return RecruitResponseDTO.CreateRecruitDTO.builder()
                .recruitId(recruitPost.getId())
                .build();
    }

    public static List<RecruitPostCategoryEnum> toCategoryList(List<SelectCategory> selectCategoryList) {
        List<RecruitPostCategoryEnum> categoryList = new ArrayList<>();
        for (SelectCategory selectCategory : selectCategoryList) {
            RecruitPostCategoryEnum category = selectCategory.getCategory();
            categoryList.add(category);
        }

        return categoryList;
    }

    public static RecruitResponseDTO.studyDetailDTO toStudyDetailDTO(User user, RecruitPost post, Room room, List<RecruitPostCategoryEnum> categoryList) {
        return RecruitResponseDTO.studyDetailDTO.builder()
                .userNickName(user.getNickname())
                .userClass(null)
                .userActive(user.getStatus())
                .inactiveTime(user.getInactiveTime())

                .views(post.getViews())
                .likes(post.getLikes())
                .bookmarks(post.getBookmarks())
                .recruitPostTypeEnum(post.getRecruitPostTypeEnum())
                .postCategoryList(categoryList)
                .recruitStartTime(post.getStartTime())
                .recruitEndTime(post.getEndTime())

                .studyName(room.getName())
                .studyDescription(null/*room.getDescription()*/)
                .studyImageUrl(null/*room.getThumbnailUrl()*/)
                .studyStartTime(room.getStartDay())
                .studyEndTime(room.getEndDay())
                .materialList(room.getMaterialList())
                .build();
    }

}
