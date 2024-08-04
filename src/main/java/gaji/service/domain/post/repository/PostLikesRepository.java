package gaji.service.domain.post.repository;


import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    void deleteByUserAndPost(User user, Post post);
}
