package gaji.service.domain.post.converter;

import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.common.service.HashtagService;
import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.CommunityPostBookMarkService;
import gaji.service.domain.post.service.CommunityPostLikesService;
import gaji.service.domain.post.service.CommunityPostQueryService;
import gaji.service.domain.post.web.dto.CommunityPostResponseDTO;
import gaji.service.domain.post.web.dto.CommunityPostRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.global.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CommunityPostConverter {
    private final HashtagService hashtagService;
    private final CategoryService categoryService;
    private final CommunityPostBookMarkService postBookMarkService;
    private final CommunityPostLikesService postLikesService;
    private final CommunityPostQueryService communityPostQueryService;

    // 초기 PostStatus 지정
    public static PostStatusEnum getInitialPostStatus(PostTypeEnum type) {
        return (type == PostTypeEnum.QUESTION) ? PostStatusEnum.NEED_RESOLUTION :
                (type == PostTypeEnum.PROJECT) ? PostStatusEnum.RECRUITING : PostStatusEnum.BLOGING;
    }

    public static CommunityPostResponseDTO.UploadPostResponseDTO toUploadPostResponseDTO(CommnuityPost post) {
        return CommunityPostResponseDTO.UploadPostResponseDTO
                .builder()
                .postId(post.getId())
                .build();
    }

    public static CommunityPostResponseDTO.PostBookmarkIdDTO toPostBookmarkIdDTO(PostBookmark postBookmark) {
        return CommunityPostResponseDTO.PostBookmarkIdDTO
                .builder()
                .postBookmarkId(postBookmark.getId())
                .build();
    }

    public static CommunityPostResponseDTO.PostLikesIdDTO toPostLikesIdDTO(PostLikes postLikes) {
        return CommunityPostResponseDTO.PostLikesIdDTO
                .builder()
                .postLikesId(postLikes.getId())
                .build();
    }

    public static CommnuityPost toPost(CommunityPostRequestDTO.UploadPostRequestDTO request, User user) {
        return CommnuityPost.builder()
                .user(user)
                .title(request.getTitle())
                .body(request.getBody())
                .thumbnailUrl(/*(request.getThumbnailUrl() == null) ? Post.getDefaultThumbnailUrl() : request.getThumbnailUrl()*/
                        request.getThumbnailUrl())
                .type(request.getType())
                .status(getInitialPostStatus(request.getType()))
                .build();
    }

    public static CommunityComment toComment(CommunityPostRequestDTO.WriteCommentRequestDTO request, User user, CommnuityPost post, CommunityComment parentComment) {
        return CommunityComment.builder()
                .user(user)
                .post(post)
                .parent(parentComment)
                .body(request.getBody())
                .build();
    }

    public static PostBookmark toPostBookmark(User user, CommnuityPost post) {
        return PostBookmark.builder()
                .user(user)
                .post(post)
                .build();
    }

    public static PostLikes toPostLikes(User user, CommnuityPost post) {
        return PostLikes.builder()
                .user(user)
                .post(post)
                .build();
    }

    public CommunityPostResponseDTO.PostPreviewDTO toPostPreviewDTO(CommnuityPost post) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithHashtagByEntityIdAndPostType(post.getId(), post.getType());
        List<String> hashtagList = HashtagConverter.toHashtagNameList(selectHashtagList);

        return CommunityPostResponseDTO.PostPreviewDTO.builder()
                .postId(post.getId())
                .likeCnt(post.getLikeCnt())
                .thumbnailUrl(post.getThumbnailUrl())
                .title(post.getTitle())
                .body(post.getBody())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickname())
                .uploadTime(DateConverter.convertToRelativeTimeFormat(post.getCreatedAt()))
                .hit(post.getHit())
                .popularityScore(post.getPopularityScore())
                .hashtagList(hashtagList)
                .build();
    }

    public CommunityPostResponseDTO.PostPreviewListDTO toPostPreviewListDTO(List<CommnuityPost> postList, boolean hasNext) {
        CommunityPostConverter postConverter = new CommunityPostConverter(hashtagService, categoryService, postBookMarkService, postLikesService, communityPostQueryService);
        List<CommunityPostResponseDTO.PostPreviewDTO> postPreviewDTOList = postList.stream()
                .map(postConverter::toPostPreviewDTO)
                .collect(Collectors.toList());

        return CommunityPostResponseDTO.PostPreviewListDTO.builder()
                .postList(postPreviewDTOList)
                .hasNext(hasNext)
                .build();
    }

    public CommunityPostResponseDTO.PostDetailDTO toPostDetailDTO(CommnuityPost post, Long userId) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithHashtagByEntityIdAndPostType(post.getId(), post.getType());
        List<HashtagResponseDTO.HashtagNameAndIdDTO> hashtagNameAndIdDTOList = HashtagConverter.toHashtagNameAndIdDTOList(selectHashtagList);

        // ofNullable 메서드로 NPE 방지
        CategoryEnum category = Optional.ofNullable(categoryService.findOneFetchJoinWithCategoryByEntityIdAndPostType(post.getId(), post.getType()))
                .map(SelectCategory::getCategory)
                .map(Category::getCategory)
                .orElse(null);

        boolean isBookmarked = (userId == null) ? false : postBookMarkService.existsByUserAndPost(userId, post);
        boolean isLiked = (userId == null) ? false : postLikesService.existsByUserAndPost(userId, post);
        boolean isWriter = (userId == null) ? false : communityPostQueryService.isPostWriter(userId, post);

        return CommunityPostResponseDTO.PostDetailDTO.builder()
                .category(category)
                .userId(post.getUser().getId())
                .type(post.getType())
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(post.getCreatedAt()), ""))
                .hit(post.getHit())
                .commentCnt(post.getCommentCnt())
                .userNickname(post.getUser().getNickname())
                .title(post.getTitle())
                .hashtagList(hashtagNameAndIdDTOList)
                .isBookMarked(isBookmarked)
                .isLiked(isLiked)
                .isWriter(isWriter)
                .body(post.getBody())
                .build();
    }
}
