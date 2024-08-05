package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

}
