package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, CommentQueryDslRepository {
}
