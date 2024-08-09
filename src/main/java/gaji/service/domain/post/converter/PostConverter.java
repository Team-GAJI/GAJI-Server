package gaji.service.domain.post.converter;

import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.service.HashtagService;
import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.PostBookMarkService;
import gaji.service.domain.post.service.PostLikesService;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.post.web.dto.PostResponseDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.global.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostConverter {
    private final HashtagService hashtagService;
    private final PostBookMarkService postBookMarkService;
    private final PostLikesService postLikesService;

    // 초기 PostStatus 지정
    public static PostStatusEnum getInitialPostStatus(PostTypeEnum type) {
        return (type == PostTypeEnum.QUESTION) ? PostStatusEnum.NEED_RESOLUTION :
                (type == PostTypeEnum.PROJECT) ? PostStatusEnum.RECRUITING : PostStatusEnum.BLOGING;
    }


    public static Post toPost(PostRequestDTO.UploadPostDTO request, User user) {
        return Post.builder()
                .user(user)
                .title(request.getTitle())
                .body(request.getBody())
                .thumbnailUrl(request.getThumbnailUrl())
                .type(request.getType())
                .status(getInitialPostStatus(request.getType()))
                .build();
    }

    public static Comment toComment(PostRequestDTO.WriteCommentDTO request, User user, Post post, Comment parentComment) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parent(parentComment)
                .body(request.getBody())
                .build();
    }

    public static PostBookmark toPostBookmark(User user, Post post) {
        return PostBookmark.builder()
                .user(user)
                .post(post)
                .build();
    }

    public static PostLikes toPostLikes(User user, Post post) {
        return PostLikes.builder()
                .user(user)
                .post(post)
                .build();
    }

    public PostResponseDTO.PostPreviewDTO toPostPreviewDTO(Post post) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithCategoryByEntityIdAndPostType(post.getId(), post.getType());
        List<String> hashtagList = HashtagConverter.toHashtagNameList(selectHashtagList);

        return PostResponseDTO.PostPreviewDTO.builder()
                .postId(post.getId())
                .likeCnt(post.getLikeCnt())
                .thumbnailUrl((post.getThumbnailUrl() == null) ? (post.settingDefaultThumbnailUrl()) : post.getThumbnailUrl())
                .title(post.getTitle())
                .body(post.getBody())
                .userId(post.getUser().getId())
                .username(post.getUser().getName())
                .uploadTime(DateConverter.convertToRelativeTimeFormat(post.getCreatedAt()))
                .viewCnt(post.getHit())
                .popularityScore(post.getPopularityScore())
                .hashtagList(hashtagList)
                .build();
    }

    public PostResponseDTO.PostPreviewListDTO toPostPreviewListDTO(List<Post> postList, boolean hasNext) {
        PostConverter postConverter = new PostConverter(hashtagService, postBookMarkService, postLikesService);
        List<PostResponseDTO.PostPreviewDTO> postPreviewDTOList = postList.stream()
                .map(postConverter::toPostPreviewDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.PostPreviewListDTO.builder()
                .postList(postPreviewDTOList)
                .hasNext(hasNext)
                .build();
    }

    public PostResponseDTO.PostDetailDTO toPostDetailDTO(Post post, Long userId) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithCategoryByEntityIdAndPostType(post.getId(), post.getType());
        List<HashtagResponseDTO.HashtagNameAndIdDTO> hashtagNameAndIdDTOList = HashtagConverter.toHashtagNameAndIdDTOList(selectHashtagList);
        boolean isBookmarked = postBookMarkService.existsByUserAndPost(userId, post);
        boolean isLiked = postLikesService.existsByUserAndPost(userId, post);

        return PostResponseDTO.PostDetailDTO.builder()
                .userId(post.getUser().getId())
                .type(post.getType())
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(post.getCreatedAt()), ""))
                .viewCnt(post.getHit())
                .commentCnt(post.getCommentCnt())
                .username(post.getUser().getName())
                .title(post.getTitle())
                .hashtagList(hashtagNameAndIdDTOList)
                .isBookMarked(isBookmarked)
                .isLiked(isLiked)
                .body(post.getBody())
                .build();
    }
}
