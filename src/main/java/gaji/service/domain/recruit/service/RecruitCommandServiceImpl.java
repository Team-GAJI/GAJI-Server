package gaji.service.domain.recruit.service;

import gaji.service.domain.User;
import gaji.service.domain.enums.RoomCategoryEnum;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.SelectCategory;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.MaterialRepository;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.studyMate.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecruitCommandServiceImpl implements RecruitCommandService {

    private final RoomRepository roomRepository;
    private final UserQueryService userQueryService;
    private final SelectCategoryRepository selectCategoryRepository;
    private final MaterialRepository materialRepository;
    private final StudyMateRepository studyMateRepository;

    private static final String DEFAULT_THUMBNAIL_URL = "https://gaji-bucket.s3.ap-northeast-2.amazonaws.com/study/gaji.png";

    @Override
    @Transactional
    public Room createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId) {
        String thumbnailUrl = DEFAULT_THUMBNAIL_URL;
        String inviteCode = null;
        int peopleMaximum = 0;
        SelectCategory selectCategory;


        if (request.getThumbnailUrl() != null && !request.getThumbnailUrl().isEmpty()) {
            thumbnailUrl = request.getThumbnailUrl();
        }

        if (request.isPrivate()) {
            inviteCode = generateInviteCode();
        }

        if (request.isPeopleLimited()) {
            peopleMaximum = request.getPeopleMaximum();
        }

        User user = userQueryService.findUserById(userId);
        Room room = RecruitConverter.toRoom(request, user, thumbnailUrl, inviteCode, peopleMaximum);

        StudyMate studyMate = RecruitConverter.toStudyMate(user, room);
        studyMateRepository.save(studyMate);

        for (RoomCategoryEnum category : request.getCategoryList()) {
            selectCategory = SelectCategory.builder()
                    .category(category)
                    .room(room)
                    .build();
            room.addCategory(selectCategory);
            selectCategoryRepository.save(selectCategory);
        }

        if (request.getMaterialList() != null && !request.getMaterialList().isEmpty()){
            Material material;
            for (String MaterialUrl : request.getMaterialList()) {
                material = RecruitConverter.toMaterial(MaterialUrl, room);
                room.addMaterial(material);
                materialRepository.save(material);
            }
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
