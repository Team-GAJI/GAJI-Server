package gaji.service.domain.recruit.service;

import gaji.service.domain.common.converter.CategoryConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.RecruitPostBookmark;
import gaji.service.domain.recruit.entity.RecruitPostLikes;
import gaji.service.domain.recruit.repository.RecruitPostBookmarkRepository;
import gaji.service.domain.recruit.repository.RecruitPostLikesRepository;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.MaterialCommandService;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.room.service.RoomQueryService;
import gaji.service.domain.studyMate.entity.StudyApplicant;
import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.service.StudyMateQueryService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import gaji.service.global.exception.RestApiException;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
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
    private final CategoryService categoryService;
    private final RoomQueryService roomQueryService;
    private final StudyMateCommandService studyMateCommandService;
    private final StudyMateQueryService studyMateQueryService;
    private final StudyApplicantService studyApplicantService;
    private final MaterialCommandService materialCommandService;
    private final RecruitPostLikesRepository recruitPostLikesRepository;
    private final RecruitPostBookmarkRepository recruitPostBookmarkRepository;

    private static final String DEFAULT_THUMBNAIL_URL = "https://gaji-bucket.s3.ap-northeast-2.amazonaws.com/study/gaji.png";

    @Override
    @Transactional
    public RecruitResponseDTO.CreateRoomResponseDTO createRoom(RecruitRequestDTO.CreateRoomDTO request, Long userId) {

        String inviteCode = null;
        int peopleMaximum = 0;

        if (request.isPrivate()) {
            inviteCode = generateInviteCode();
        }

        if (request.isPeopleLimited()) {
            peopleMaximum = request.getPeopleMaximum();
        }

        User user = userQueryService.findUserById(userId);
        Room room = RecruitConverter.toRoom(request, user, request.getThumbnailUrl(), inviteCode, peopleMaximum);

        StudyMate studyMate = RecruitConverter.toStudyMate(user, room);
        studyMateCommandService.saveStudyMate(studyMate);

        if (request.getMaterialList() != null && !request.getMaterialList().isEmpty()){
            Material material;
            for (String MaterialUrl : request.getMaterialList()) {
                material = RecruitConverter.toMaterial(MaterialUrl, room);
                room.addMaterial(material);
                materialCommandService.saveMaterial(material);
            }
        }

        roomCommandService.saveRoom(room);

        if (request.getCategoryId() == null) {
            throw new RestApiException(GlobalErrorStatus._INVALID_CATEGORY);
        }

        Long categoryId = request.getCategoryId();
        Category category = categoryService.findByCategoryId(categoryId);

        SelectCategory selectCategory = CategoryConverter.toSelectCategory(category, room.getId(), PostTypeEnum.ROOM);
        categoryService.saveSelectCategory(selectCategory);

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

    @Override
    @Transactional
    public RecruitResponseDTO.StudyLikesIdResponseDTO likeStudy(Long userId, Long roomId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (recruitPostLikesRepository.existsByUserAndRoom(user, room)) {
            throw new RestApiException(RecruitErrorStatus._ROOM_ALREADY_LIKE);
        }

        RecruitPostLikes studyLikes = recruitPostLikesRepository.save(RecruitConverter.toRecruitPostLikes(user, room));
        room.increaseLike();

        return RecruitConverter.toStudyLikesIdDTO(studyLikes);
    }

    @Override
    @Transactional
    public void unLikeStudy(Long userId, Long roomId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (!recruitPostLikesRepository.existsByUserAndRoom(user, room)) {
            throw new RestApiException(RecruitErrorStatus._ROOM_ALREADY_NO_LIKE);
        }

        recruitPostLikesRepository.deleteByUserAndRoom(user, room);
        room.decreaseLike();
    }

    @Override
    @Transactional
    public RecruitResponseDTO.StudyBookmarkIdDTO bookmarkStudy(Long userId, Long roomId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (recruitPostBookmarkRepository.existsByUserAndRoom(user, room)) {
            throw new RestApiException(RecruitErrorStatus._ROOM_ALREADY_BOOKMARK);
        }

        RecruitPostBookmark studyBookmark = recruitPostBookmarkRepository.save(RecruitConverter.toRecruitPostBookmark(user, room));
        room.increaseBookmark();

        return RecruitConverter.toStudyBookmarkIdDTO(studyBookmark);
    }

    @Override
    @Transactional
    public void unBookmarkStudy(Long userId, Long roomId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (!recruitPostBookmarkRepository.existsByUserAndRoom(user, room)) {
            throw new RestApiException(RecruitErrorStatus._ROOM_ALREADY_NO_BOOKMARK);
        }
        recruitPostBookmarkRepository.deleteByUserAndRoom(user, room);
        room.decreaseBookmark();
    }
}
