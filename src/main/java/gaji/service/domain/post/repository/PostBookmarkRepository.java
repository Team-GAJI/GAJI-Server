package gaji.service.domain.post.repository;

import gaji.service.domain.User;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {

    void deleteByUserAndPost(User user, Post post);
}
