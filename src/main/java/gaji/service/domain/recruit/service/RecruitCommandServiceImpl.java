package gaji.service.domain.recruit.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.SelectCategory;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecruitCommandServiceImpl implements RecruitCommandService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SelectCategoryRepository selectCategoryRepository;

    @Override
    @Transactional
    public Room createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId) {

        String inviteCode = null;
        int peopleMaximum = 0;
        SelectCategory selectCategory;

        if (request.isPrivate()) {
            inviteCode = generateInviteCode();
        }

        if (request.isPeopleLimited()) {
            peopleMaximum = request.getPeopleMaximum();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(RecruitErrorStatus._USER_NOT_FOUND));

        Room room = RecruitConverter.toRoom(request, user, inviteCode, peopleMaximum);

        for (RecruitPostCategoryEnum category : request.getCategoryList()) {
            selectCategory = SelectCategory.builder()
                    .category(category)
                    .recruitPost(room)
                    .build();
            room.addCategory(selectCategory);
            selectCategoryRepository.save(selectCategory);
        }

        roomRepository.save(room);
        return room;
    }

    private String generateInviteCode() {
        int CODE_LENGTH = 6;
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        Random random = new SecureRandom();
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARS.length());
            code.append(CHARS.charAt(index));
        }

        return code.toString();
    }
}
