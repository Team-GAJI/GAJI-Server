package gaji.service.domain.post.repository;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

public interface PostQueryDslRepository {

    Slice<Post> findAllFetchJoinWithUser(Integer lastPopularityScore,
                                         Long lastPostId,
                                         Integer lastLikeCnt,
                                         Integer lastHit,
                                         PostTypeEnum postType,
                                         PostStatusEnum postStatus,
                                         Long categoryId,
                                         SortType sortType,
                                         Pageable pageable);
    Post findByIdFetchJoinWithUser(Long postId);

    Slice<Tuple> findAllPostsByUser(User user, LocalDateTime CursorDateTime, Pageable pageable, PostTypeEnum type);
}

