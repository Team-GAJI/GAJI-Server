package gaji.service.domain.room.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.room.repository.RoomRepositoryCustom;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryServiceImpl implements RoomQueryService, RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final RoomRepository roomRepository;

    @Override
    public Room findRoomById(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));
    }


    @Override
    public RoomResponseDto.StudyRoomInfoDTO getStudyRoomInfo(Long roomId) {
        QRoom room = QRoom.room;
        QSelectHashtag selectHashtag = QSelectHashtag.selectHashtag;
        QHashtag hashtag = QHashtag.hashtag;
        QStudyApplicant studyApplicant = QStudyApplicant.studyApplicant;

        Room result = queryFactory
                .selectFrom(room)
                .leftJoin(room.recruitPostList).fetchJoin()
                .where(room.id.eq(roomId))
                .fetchOne();

        if (result == null) {
            return null;
        }

        List<String> hashtags = queryFactory
                .select(hashtag.name)
                .from(selectHashtag)
                .join(selectHashtag.hashtag, hashtag)
                .where(selectHashtag.entityId.eq(roomId)
                        .and(selectHashtag.type.eq(PostTypeEnum.ROOM)))
                .fetch();

        long applicantCount = queryFactory
                .selectFrom(studyApplicant)
                .where(studyApplicant.room.id.eq(roomId))
                .fetchCount();

        LocalDate recruitmentEndDate = result.getRecruitPostList().stream()
                .map(RecruitPost::getEndDate)
                .max(LocalDate::compareTo)
                .orElse(null);

        long daysUntilDeadline = recruitmentEndDate != null
                ? ChronoUnit.DAYS.between(LocalDate.now(), recruitmentEndDate)
                : 0;

        return new StudyRoomInfoDTO(
                result.getName(),
                hashtags,
                recruitmentEndDate,
                daysUntilDeadline,
                applicantCount
        );
    }
}
