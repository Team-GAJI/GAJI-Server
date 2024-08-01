package gaji.service.domain.roomPost.service;

import gaji.service.domain.User;
import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.repository.RoomRepository;
import gaji.service.domain.roomPost.entity.RoomPost;
import gaji.service.domain.roomPost.converter.RoomPostConverter;
import gaji.service.domain.roomPost.repository.RoomPostRepository;
import gaji.service.domain.roomPost.web.dto.RoomPostRequestDto;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomPostCommandServiceImpl implements RoomPostCommandService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final StudyMateRepository studyMateRepository;
    private final RoomPostRepository roomPostRepository;


    @Override
    public RoomPost createRoomPost(Long roomId, Long userId, RoomPostRequestDto.RoomPostDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));

        // 스터디룸 존재 여부 확인
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._ROOM_NOT_FOUND));

        // 사용자가 해당 스터디룸에 참여하고 있는지 확인
        studyMateRepository.findByUserIdAndRoomId(user.getId(), roomId)
                .orElseThrow(() -> new RestApiException(RoomErrorStatus._USER_NOT_IN_ROOM));

        RoomPost roomPost = RoomPostConverter.toPost(requestDto,user);
        roomPostRepository.save(roomPost);

        return roomPost;

    }

}
