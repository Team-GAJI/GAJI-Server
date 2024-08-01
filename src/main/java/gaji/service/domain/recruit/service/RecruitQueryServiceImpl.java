package gaji.service.domain.recruit.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.RecruitPost;
import gaji.service.domain.recruit.entity.SelectCategory;
import gaji.service.domain.recruit.repository.RecruitRepository;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public RecruitResponseDTO.studyDetailDTO getStudyDetail(Long postId) {
        RecruitPost post = recruitRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._RECRUIT_POST_NOT_FOUND));

        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._USER_NOT_FOUND));

        Room room = roomRepository.findById(post.getRoom().getId())
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));

        post.addView();
        System.out.println(post.getViews());
        recruitRepository.save(post);

        List<RecruitPostCategoryEnum> categoryList = RecruitConverter.toCategoryList(post.getSelectCategoryList());

        return RecruitConverter.toStudyDetailDTO(user, post, room, categoryList);
    }

}
