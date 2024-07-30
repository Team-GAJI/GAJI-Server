package gaji.service.domain.post.web.controller;

import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.PostCommandService;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


//@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community-posts")
public class PostRestController {

    private final PostCommandService postCommandService;

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 업로드 API", description = "커뮤니티의 게시글을 업로드하는 API입니다. 게시글 유형과 제목, 본문 내용을 검증합니다.")
    public BaseResponse<Long> uploadPost(Long userId,
                                         @RequestBody @Valid PostRequestDTO.UploadPostDTO request) {
        Post newPost = postCommandService.uploadPost(userId, request);
        return BaseResponse.onSuccess(newPost.getId());
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성 API", description = "커뮤니티의 게시글에 댓글을 작성하는 API입니다. 대댓글을 작성하는 거라면 Long 타입의 parentCommentId를 query parameter로 보내주시면 됩니다!")
    @Parameters({
            @Parameter(name = "userId", description = "회원 id"),
            @Parameter(name = "postId", description = "게시글 id"),
            @Parameter(name = "parentCommentId", description = "부모 댓글의 id, 대댓글 작성할 때 필요한 부모 댓글의 id입니다."),
    })
    public BaseResponse<Long> writeCommentOnCommunityPost(@RequestParam Long userId,
                                                          @PathVariable Long postId,
                                                          @RequestParam(required = false) Long parentCommentId,
                                                          @RequestBody @Valid PostRequestDTO.WriteCommentDTO request) {
        Comment newComment = postCommandService.writeCommentOnCommunityPost(userId, postId, parentCommentId, request);
        return BaseResponse.onSuccess(newComment.getId());
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "커뮤니티 게시글 댓글 삭제 API", description = "커뮤니티 게시글의 댓글을 삭제하는 API입니다. Comment를 DB에서 바로 삭제하지 않고 status를 DELETE로만 바꿉니다.(soft delete)")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 id"),
    })
    public BaseResponse softDeleteComment(Long userId, @PathVariable Long commentId) {
        postCommandService.softDeleteComment(commentId);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제 API", description = "커뮤니티 게시글을 삭제하는 API입니다. 게시글과 관련된 북마크, 좋아요, 댓글 내역을 모두 삭제합니다.(hard delete)")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse hardDeleteCommunityPost(Long userId, @PathVariable Long postId) {
        postCommandService.hardDeleteCommunityPost(postId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 API", description = "커뮤니티 게시글을 북마크하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<Long> bookmarkCommunityPost(Long userId, @PathVariable Long postId) {
        PostBookmark newPostBookmark = postCommandService.bookmarkCommunityPost(userId, postId);
        return BaseResponse.onSuccess(newPostBookmark.getId());
    }

    @DeleteMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 취소 API", description = "커뮤니티 게시글을 북마크 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelBookmarkCommunityPost(Long userId, @PathVariable Long postId) {
        postCommandService.cancelbookmarkCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 API", description = "커뮤니티 게시글을 좋아요하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<Long> likeCommunityPost(Long userId, @PathVariable Long postId) {
        PostLikes newPostLikes = postCommandService.likeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(newPostLikes.getId());
    }

    @DeleteMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 취소 API", description = "커뮤니티 게시글을 좋아요 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelLikeCommunityPost(Long userId, @PathVariable Long postId) {
        postCommandService.cancelLikeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }



}
