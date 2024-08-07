package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;

import java.util.List;

public interface CommentQueryDslRepository {
    List<Comment> findAllByPostFetchJoinWithUser(Post post);
}
