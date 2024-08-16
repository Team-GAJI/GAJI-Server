package gaji.service.domain.post.converter;

import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.service.HashtagService;
import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.CommunityPostBookMarkService;
import gaji.service.domain.post.service.CommunityPostLikesService;
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
public class CommunityPostConverter {
    private final HashtagService hashtagService;
    private final CommunityPostBookMarkService postBookMarkService;
    private final CommunityPostLikesService postLikesService;

    // 초기 PostStatus 지정
    public static PostStatusEnum getInitialPostStatus(PostTypeEnum type) {
        return (type == PostTypeEnum.QUESTION) ? PostStatusEnum.NEED_RESOLUTION :
                (type == PostTypeEnum.PROJECT) ? PostStatusEnum.RECRUITING : PostStatusEnum.BLOGING;
    }

    public static PostResponseDTO.UploadPostDTO toUploadPostDTO(CommnuityPost post) {
        return PostResponseDTO.UploadPostDTO
                .builder()
                .postId(post.getId())
                .build();
    }

    public static PostResponseDTO.PostBookmarkIdDTO toPostBookmarkIdDTO(PostBookmark postBookmark) {
        return PostResponseDTO.PostBookmarkIdDTO
                .builder()
                .postBookmarkId(postBookmark.getId())
                .build();
    }

    public static PostResponseDTO.PostLikesIdDTO toPostLikesIdDTO(PostLikes postLikes) {
        return PostResponseDTO.PostLikesIdDTO
                .builder()
                .postLikesId(postLikes.getId())
                .build();
    }

    public static CommnuityPost toPost(PostRequestDTO.UploadPostDTO request, User user) {
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

    public static CommunityComment toComment(PostRequestDTO.WriteCommentDTO request, User user, CommnuityPost post, CommunityComment parentComment) {
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

    public PostResponseDTO.PostPreviewDTO toPostPreviewDTO(CommnuityPost post) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithHashtagByEntityIdAndPostType(post.getId(), post.getType());
        List<String> hashtagList = HashtagConverter.toHashtagNameList(selectHashtagList);

        return PostResponseDTO.PostPreviewDTO.builder()
                .postId(post.getId())
                .likeCnt(post.getLikeCnt())
                .thumbnailUrl(post.getThumbnailUrl())
                .title(post.getTitle())
                .body(post.getBody())
                .userId(post.getUser().getId())
                .username(post.getUser().getName())
                .uploadTime(DateConverter.convertToRelativeTimeFormat(post.getCreatedAt()))
                .hit(post.getHit())
                .popularityScore(post.getPopularityScore())
                .hashtagList(hashtagList)
                .build();
    }

    public PostResponseDTO.PostPreviewListDTO toPostPreviewListDTO(List<CommnuityPost> postList, boolean hasNext) {
        CommunityPostConverter postConverter = new CommunityPostConverter(hashtagService, postBookMarkService, postLikesService);
        List<PostResponseDTO.PostPreviewDTO> postPreviewDTOList = postList.stream()
                .map(postConverter::toPostPreviewDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.PostPreviewListDTO.builder()
                .postList(postPreviewDTOList)
                .hasNext(hasNext)
                .build();
    }

    public PostResponseDTO.PostDetailDTO toPostDetailDTO(CommnuityPost post, Long userId) {
        List<SelectHashtag> selectHashtagList = hashtagService.findAllFetchJoinWithHashtagByEntityIdAndPostType(post.getId(), post.getType());
        List<HashtagResponseDTO.HashtagNameAndIdDTO> hashtagNameAndIdDTOList = HashtagConverter.toHashtagNameAndIdDTOList(selectHashtagList);
        boolean isBookmarked = (userId == null) ? false : postBookMarkService.existsByUserAndPost(userId, post);
        boolean isLiked = (userId == null) ? false : postLikesService.existsByUserAndPost(userId, post);

        return PostResponseDTO.PostDetailDTO.builder()
                .userId(post.getUser().getId())
                .type(post.getType())
                .createdAt(DateConverter.convertWriteTimeFormat(LocalDate.from(post.getCreatedAt()), ""))
                .hit(post.getHit())
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
