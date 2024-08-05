package gaji.service.domain.post.web.controller;

import gaji.service.domain.common.enums.SortType;
import gaji.service.domain.common.service.HashtagQueryService;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.PostCommandService;
import gaji.service.domain.post.service.PostQueryService;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.post.web.dto.PostResponseDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community-posts")
public class PostRestController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
    private final HashtagQueryService hashtagQueryService;
    private final TokenProviderService tokenProviderService;
    private final PostConverter postConverter;

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 업로드 API", description = "커뮤니티의 게시글을 업로드하는 API입니다. 게시글 유형과 제목, 본문 내용을 검증합니다.")
    public BaseResponse<Long> uploadPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
                                         @RequestParam Long userId,
                                         @RequestBody @Valid PostRequestDTO.UploadPostDTO request) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        Post newPost = postCommandService.uploadPost(userId, request);
        return BaseResponse.onSuccess(newPost.getId());
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성 API", description = "커뮤니티의 게시글에 댓글을 작성하는 API입니다. 대댓글을 작성하는 거라면 Long 타입의 parentCommentId를 query parameter로 보내주시면 됩니다!")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
            @Parameter(name = "parentCommentId", description = "부모 댓글의 id, 대댓글 작성할 때 필요한 부모 댓글의 id입니다."),
    })
    public BaseResponse<Long> writeCommentOnCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                          @PathVariable Long postId,
                                                          @RequestParam(required = false) Long parentCommentId,
                                                          @RequestBody @Valid PostRequestDTO.WriteCommentDTO request) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        Comment newComment = postCommandService.writeCommentOnCommunityPost(userId, postId, parentCommentId, request);
        return BaseResponse.onSuccess(newComment.getId());
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "커뮤니티 게시글 댓글 삭제 API", description = "커뮤니티 게시글의 댓글을 삭제하는 API입니다. Comment를 DB에서 바로 삭제하지 않고 status를 DELETE로만 바꿉니다.(soft delete)")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 id"),
    })
    public BaseResponse softDeleteComment(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                          @PathVariable Long commentId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        postCommandService.softDeleteComment(commentId);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제 API", description = "커뮤니티 게시글을 삭제하는 API입니다. 게시글과 관련된 북마크, 좋아요, 댓글 내역을 모두 삭제합니다.(hard delete)")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse hardDeleteCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                @PathVariable Long postId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        postCommandService.hardDeleteCommunityPost(postId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 API", description = "커뮤니티 게시글을 북마크하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<Long> bookmarkCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                    @PathVariable Long postId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        PostBookmark newPostBookmark = postCommandService.bookmarkCommunityPost(userId, postId);
        return BaseResponse.onSuccess(newPostBookmark.getId());
    }

    @DeleteMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 취소 API", description = "커뮤니티 게시글을 북마크 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelBookmarkCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                    @PathVariable Long postId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        postCommandService.cancelbookmarkCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 API", description = "커뮤니티 게시글을 좋아요하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<Long> likeCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                @PathVariable Long postId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        PostLikes newPostLikes = postCommandService.likeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(newPostLikes.getId());
    }

    @DeleteMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 취소 API", description = "커뮤니티 게시글을 좋아요 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelLikeCommunityPost(/*@RequestHeader("Authorization") String authorizationHeader,*/
            @RequestParam Long userId,
                                                @PathVariable Long postId) {
//        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        postCommandService.cancelLikeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/preivew")
    public BaseResponse<List<PostResponseDTO.PostPreviewDTO>> getPostPreivewList(@RequestParam(required = false) PostTypeEnum postType,
                                                                                 @RequestParam(required = false) String category,
                                                                                 @RequestParam(required = false) SortType sortType,
                                                                                 @RequestParam(required = false) PostStatusEnum filter) {

        List<Post> postList = postQueryService.getPostList(postType, category, sortType, filter);
        return BaseResponse.onSuccess(postConverter.toPostPreviewDTOList(postList));
    }


}
