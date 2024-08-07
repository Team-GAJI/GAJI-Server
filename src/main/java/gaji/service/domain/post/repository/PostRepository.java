package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findFirstByUserOrderByCreatedAtDesc(User user);
}
