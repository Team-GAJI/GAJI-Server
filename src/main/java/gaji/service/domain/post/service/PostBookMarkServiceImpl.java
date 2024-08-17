package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.repository.PostBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostBookMarkServiceImpl implements PostBookMarkService {
    private final PostBookmarkRepository postBookmarkRepository;

    @Override
    public boolean existsByUserAndPost(Long userId, Post post) {
        return postBookmarkRepository.existsByUserIdAndPost(userId, post);
    }
}
