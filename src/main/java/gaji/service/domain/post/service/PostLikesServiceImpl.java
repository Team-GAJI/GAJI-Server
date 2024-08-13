package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostLikesRepository;
import gaji.service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikesServiceImpl implements PostLikesService {
    private final PostLikesRepository postLikesRepository;

    @Override
    public boolean existsByUserAndPost(Long userId, Post post) {
        return postLikesRepository.existsByUserIdAndPost(userId, post);
    }
}
