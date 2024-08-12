package gaji.service.domain.post.repository;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface CommentQueryDslRepository {
    Slice<Comment> findBySliceAndPostFetchJoinWithUser(Integer lastGroupNum, Post post, Pageable pageable);
}
