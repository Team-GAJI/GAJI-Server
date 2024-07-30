package gaji.service.domain.post.service;

import gaji.service.domain.User;
import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.repository.HashtagRepository;
import gaji.service.domain.common.repository.SelectHashtagRepository;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostRepository;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final SelectHashtagRepository selectHashtagRepository;

    @Override
    public Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));
        Post post = PostConverter.toPost(request, user);
        Post newPost = postRepository.save(post);

        List<String> hashtagStringList = request.getHashtagList();
        List<Hashtag> hashtagEntityList = getHashtagListByhashtagStringList(hashtagStringList);

        List<SelectHashtag> selectHashtagList = HashtagConverter.toSelectHashtagList(hashtagEntityList, post.getId(), request.getType());
        selectHashtagRepository.saveAll(selectHashtagList);

        return newPost;
    }

    // 이미 존재하는 해시태그는 조회, 존재하지 않는 해시태그는 생성해서 List로 반환하는 메서드
    private List<Hashtag> getHashtagListByhashtagStringList(List<String> hashtagStringList) {
        return hashtagStringList.stream()
                .map(hashtag -> {
                    if (hashtagRepository.existsByName(hashtag)) {
                        return hashtagRepository.findByName(hashtag);
                    } else {
                        return hashtagRepository.save(HashtagConverter.toHashtag(hashtag));
                    }
                })
                .collect(Collectors.toList());
    }
}
