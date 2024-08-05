package gaji.service.domain.post.converter;

import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.service.HashtagQueryService;
import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.post.web.dto.PostResponseDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.global.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostConverter {
    private final HashtagQueryService hashtagQueryService;

    // 초기 PostStatus 지정
    public static PostStatusEnum getInitialPostStatus(PostTypeEnum type) {
        return (type == PostTypeEnum.QUESTION) ? PostStatusEnum.NEED_RESOLUTION :
                (type == PostTypeEnum.PROJECT) ? PostStatusEnum.RECRUITING : PostStatusEnum.POSTING;
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
        List<SelectHashtag> selectHashtagList = hashtagQueryService.findAllFetchJoinWithCategoryByEntityIdAndPostType(post.getId(), post.getType());
        List<HashtagResponseDTO.BaseResponseDTO> hashtagList = selectHashtagList.stream()
                .map(HashtagConverter::toBaseResponseDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.PostPreviewDTO.builder()
                .postId(post.getId())
                .likeCnt(post.getLikeCnt())
                .thumbnailUrl(post.getThumbnailUrl())
                .title(post.getTitle())
                .body(post.getBody())
                .username(post.getUser().getName())
                .uploadTime(DateConverter.convertToRelativeTimeFormat(post.getCreatedAt()))
                .viewCnt(post.getViewCnt())
                .hashtagList(hashtagList)
                .build();
    }

    public List<PostResponseDTO.PostPreviewDTO> toPostPreviewDTOList(List<Post> postList) {
        PostConverter postConverter = new PostConverter(hashtagQueryService);
        return postList.stream()
                .map(post -> postConverter.toPostPreviewDTO(post))
                .collect(Collectors.toList());
    }
}
