package gaji.service.domain.recruit.converter;

import gaji.service.domain.User;
import gaji.service.domain.enums.RoomCategoryEnum;
import gaji.service.domain.recruit.entity.SelectCategory;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;

import java.util.ArrayList;
import java.util.List;

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

    public static List<RoomCategoryEnum> toCategoryList(List<SelectCategory> selectCategoryList) {
        List<RoomCategoryEnum> categoryList = new ArrayList<>();
        for (SelectCategory selectCategory : selectCategoryList) {
            RoomCategoryEnum category = selectCategory.getCategory();
            categoryList.add(category);
        }

        return categoryList;
    }

    public static Material toMaterial(String materialPath, Room room) {
        return Material.builder()
                .room(room)
                .path(materialPath)
                .build();
    }

    public static RecruitResponseDTO.studyDetailDTO toStudyDetailDTO(User user, Room room, List<RoomCategoryEnum> categoryList) {
        return RecruitResponseDTO.studyDetailDTO.builder()
                .userNickName(user.getNickname())
                .userClass(null)
                .userActive(user.getStatus())
                .inactiveTime(user.getInactiveTime())

                .views(room.getViews())
                .likes(room.getLikes())
                .bookmarks(room.getBookmarks())
                .recruitPostTypeEnum(room.getRecruitPostTypeEnum())
                .postCategoryList(categoryList)
                .recruitStartTime(room.getRecruitStartDay())
                .recruitEndTime(room.getRecruitEndDay())

                .studyName(room.getName())
                .studyDescription(null/*room.getDescription()*/)
                .studyImageUrl(null/*room.getThumbnailUrl()*/)
                .studyStartTime(room.getStudyStartDay())
                .studyEndTime(room.getStudyEndDay())
                .materialList(room.getMaterialList())
                .build();
    }

}
