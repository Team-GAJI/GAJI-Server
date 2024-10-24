package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.repository.CommunityPostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostLikesServiceImpl implements CommunityPostLikesService {
    private final CommunityPostLikesRepository postLikesRepository;

    @Override
    public boolean existsByUserAndPost(Long userId, CommnuityPost post) {
        return postLikesRepository.existsByUserIdAndPost(userId, post);
    }
}
