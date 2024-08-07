package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostQueryDslRepository {

}
