package gaji.service.domain.post.service;

import com.querydsl.core.Tuple;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;


public interface CommunityPostQueryService {

    CommnuityPost findPostByPostId(Long postId);
    Slice<Tuple> getAllPostByUserAndType(User user, LocalDateTime cursorDateTime, Pageable pageable, PostTypeEnum type);
    Slice<CommnuityPost> getPostList(String keyword,
                            Integer lastPopularityScore,
                            Long lastPostId,
                            Integer lastLikeCnt,
                            Integer lastHit,
                            PostTypeEnum postType,
                            String category,
                            SortType sortType,
                            PostStatusEnum filter,
                            int page,
                            int size);
    CommnuityPost getPostDetail(Long postId);
    boolean isPostWriter(Long userId, CommnuityPost post);
    void validPostWriter(Long userId, CommnuityPost post);
    void validExistsPostLikes(Long userId, CommnuityPost post);
    void validExistsPostBookmark(Long userId, CommnuityPost post);

}
