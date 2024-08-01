package gaji.service.domain.recruit.converter;

import gaji.service.domain.User;
import gaji.service.domain.recruit.entity.RecruitPost;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;

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
}
