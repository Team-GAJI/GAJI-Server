package gaji.service.domain.recruit.service;

import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.repository.CategoryRepository;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.repository.SelectCategoryRepository;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.MaterialRepository;
import gaji.service.domain.room.service.MaterialCommandService;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.studyMate.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecruitCommandServiceImpl implements RecruitCommandService {

    private final RoomCommandService roomCommandService;
    private final UserQueryService userQueryService;
    private final SelectCategoryRepository selectCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final StudyMateRepository studyMateRepository;
    private final MaterialCommandService materialCommandService;

    private static final String DEFAULT_THUMBNAIL_URL = "https://gaji-bucket.s3.ap-northeast-2.amazonaws.com/study/gaji.png";

    @Override
    @Transactional
    public RecruitResponseDTO.CreateRoomDTO createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId) {
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

        if (request.getMaterialList() != null && !request.getMaterialList().isEmpty()){
            Material material;
            for (String MaterialUrl : request.getMaterialList()) {
                material = RecruitConverter.toMaterial(MaterialUrl, room);
                room.addMaterial(material);
                materialCommandService.saveMaterial(material);
            }
        }

        roomCommandService.saveRoom(room);

        for (CategoryEnum categoryEnum : request.getCategoryList()) {
            Category category = Category.builder()
                    .category(categoryEnum)
                    .build();
//            categoryRepository.save(category);
            selectCategory = SelectCategory.builder()
//                    .category(category)
                    .entityId(room.getId())
                    .type(PostTypeEnum.ROOM)
                    .build();
            selectCategoryRepository.save(selectCategory);
        }

        return RecruitConverter.toResponseDTO(room);
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
