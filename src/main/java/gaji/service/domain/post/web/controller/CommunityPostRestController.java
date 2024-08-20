package gaji.service.domain.post.web.controller;

import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.enums.PostStatusEnum;
import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.enums.SortType;
import gaji.service.domain.post.converter.CommunityCommentConverter;
import gaji.service.domain.post.converter.CommunityPostConverter;
import gaji.service.domain.post.entity.CommnuityPost;
import gaji.service.domain.post.entity.CommunityComment;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.service.CommunityCommentService;
import gaji.service.domain.post.service.CommunityPostCommandService;
import gaji.service.domain.post.service.CommunityPostQueryService;
import gaji.service.domain.post.web.dto.CommunityPostCommentResponseDTO;
import gaji.service.domain.post.web.dto.CommunityPostResponseDTO;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.global.base.BaseResponse;
import gaji.service.jwt.service.TokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community-posts")
public class CommunityPostRestController {

    private final CommunityPostCommandService communityPostCommandService;
    private final CommunityPostQueryService communityPostQueryService;
    private final CommunityCommentService commentService;
    private final TokenProviderService tokenProviderService;
    private final CommunityPostConverter communityPostConverter;

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 업로드 API", description = "커뮤니티의 게시글을 업로드하는 API입니다. 게시글 유형과 제목, 본문 내용을 검증합니다.")
    public BaseResponse<CommunityPostResponseDTO.UploadPostDTO> uploadPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                           @RequestBody @Valid PostRequestDTO.UploadPostDTO request) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        CommnuityPost newPost = communityPostCommandService.uploadPost(userId, request);
        return BaseResponse.onSuccess(CommunityPostConverter.toUploadPostDTO(newPost));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제 API", description = "커뮤니티 게시글을 삭제하는 API입니다. 게시글과 관련된 북마크, 좋아요, 댓글 내역을 모두 삭제합니다.(hard delete)")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse hardDeleteCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
            @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        communityPostCommandService.hardDeleteCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 상세 조회 API", description = "댓글을 제외한 게시글의 상세 정보를 조회합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<CommunityPostResponseDTO.PostDetailDTO> getPostDetail(@Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId,
                                                                              @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        Long userId = (authorizationHeader == null) ? null : tokenProviderService.getUserIdFromToken(authorizationHeader);
        CommnuityPost post = communityPostQueryService.getPostDetail(postId);
        SelectCategory category = categoryService.findByEntityIdAndType(post.getId(), post.getType());
        return BaseResponse.onSuccess(communityPostConverter.toPostDetailDTO(post, userId, category));
    }

    @GetMapping("/preivew")
    @Operation(summary = "커뮤니티 게시글 미리보기 목록 조회 API", description = "아직은 무한스크롤로 구현되어있지 않고, 모든 목록을 조회합니다.")
    @Parameters({
            @Parameter(name = "lastPopularityScore", description = "마지막으로 조회한 게시글의 인기 점수"),
            @Parameter(name = "lastPostId", description = "마지막으로 조회한 게시글의 id"),
            @Parameter(name = "lastLikeCnt", description = "마지막으로 조회한 게시글의 좋아요 수"),
            @Parameter(name = "lastHit", description = "마지막으로 조회한 게시글의 조회수"),
            @Parameter(name = "postType", description = "게시글의 유형(블로그, 프로젝트, 질문)"),
            @Parameter(name = "categoryId", description = "카테고리(DEVELOP, AI, HW, ... 정책 공통 사항 참조)의 id"),
            @Parameter(name = "sortType", description = "정렬 유형(hot, recent, like, hit)"),
            @Parameter(name = "filter", description = "게시글의 상태(모집중, 모집완료, 미완료질문, 해결완료)"),
    })
    public BaseResponse<CommunityPostResponseDTO.PostPreviewListDTO> getPostPreivewList(@Min(value = 0, message = "lastPopularityScore는 0 이상 이어야 합니다.") @RequestParam(required = false) Integer lastPopularityScore,
                                                                                        @Min(value = 1, message = "lastPostId는 1 이상 이어야 합니다.") @RequestParam(required = false) Long lastPostId,
                                                                                        @Min(value = 0, message = "lastLikeCnt는 0 이상 이어야 합니다.") @RequestParam(required = false) Integer lastLikeCnt,
                                                                                        @Min(value = 0, message = "lastHit은 0 이상 이어야 합니다.") @RequestParam(required = false) Integer lastHit,
                                                                                        @RequestParam(required = false) PostTypeEnum postType,
                                                                                        @RequestParam(required = false) String category,
                                                                                        @RequestParam(required = false, defaultValue = "recent") SortType sortType,
                                                                                        @RequestParam(required = false) PostStatusEnum filter,
                                                                                        @Min(value = 0, message = "page는 0 이상 이어야 합니다.") @RequestParam(defaultValue = "0") int page,
                                                                                        @Min(value = 1, message = "size는 1 이상 이어야 합니다.") @RequestParam(defaultValue = "10") int size) {

        Slice<CommnuityPost> postSlice = communityPostQueryService.getPostList(lastPopularityScore, lastPostId, lastLikeCnt, lastHit, postType, category, sortType, filter, page, size);
        return BaseResponse.onSuccess(communityPostConverter.toPostPreviewListDTO(postSlice.getContent(), postSlice.hasNext()));
    }

    @GetMapping("/search")
    public BaseResponse<CommunityPostResponseDTO.PostPreviewListDTO> searchCommunityPostList() {
        return null;
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성 API", description = "커뮤니티의 게시글에 댓글을 작성하는 API입니다. 대댓글을 작성하는 거라면 Long 타입의 parentCommentId를 query parameter로 보내주시면 됩니다!")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
            @Parameter(name = "parentCommentId", description = "부모 댓글의 id, 대댓글 작성할 때 필요한 부모 댓글의 id입니다."),
    })
    public BaseResponse<CommunityPostCommentResponseDTO.WriteCommentDTO> writeCommentOnCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                                     @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId,
                                                                                                     @Min(value = 1, message = "parentCommentId는 1 이상 이어야 합니다.") @RequestParam(required = false) Long parentCommentId,
                                                                                                     @RequestBody @Valid PostRequestDTO.WriteCommentDTO request) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        CommunityComment newComment = communityPostCommandService.writeCommentOnCommunityPost(userId, postId, parentCommentId, request);
        return BaseResponse.onSuccess(CommunityCommentConverter.toWriteCommentDTO(newComment));
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "커뮤니티 게시글 댓글 삭제 API", description = "커뮤니티 게시글의 댓글을 삭제하는 API입니다.(hard delete)")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 id"),
    })
    public BaseResponse hardDeleteComment(@RequestHeader("Authorization") String authorizationHeader,
                                          @Min(value = 1, message = "commentId는 1 이상 이어야 합니다.") @PathVariable Long commentId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        communityPostCommandService.hardDeleteComment(userId, commentId);
        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 목록 조회 API", description = "lastGroupNum에 마지막으로 조회한 댓글의 grounNum과, size로 조회할 데이터의 개수를 보내주세요.")
    public BaseResponse<CommunityPostCommentResponseDTO.PostCommentListDTO> getCommentList(@Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId,
                                                                                           @Min(value = 0, message = "lastGroupNum은 0 이상 이어야 합니다.") @RequestParam(required = false) Integer lastGroupNum, // 마지막 댓글 ID
                                                                                           @Min(value = 0, message = "page는 0 이상 이어야 합니다.") @RequestParam(defaultValue = "0") int page,
                                                                                           @Min(value = 1, message = "size는 1 이상 이어야 합니다.") @RequestParam(defaultValue = "10") int size) // 페이지 크기 (기본값 10))
    {
        Slice<CommunityComment> commentSlice = commentService.getCommentListByPost(postId, lastGroupNum, page, size);
        CommunityPostCommentResponseDTO.PostCommentListDTO postCommentDTOList = CommunityCommentConverter.toPostCommentListDTO(commentSlice.getContent(), commentSlice.hasNext());
        return BaseResponse.onSuccess(postCommentDTOList);
    }

    @PostMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 API", description = "커뮤니티 게시글을 북마크하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<CommunityPostResponseDTO.PostBookmarkIdDTO> bookmarkCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                          @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        PostBookmark newPostBookmark = communityPostCommandService.bookmarkCommunityPost(userId, postId);

        return BaseResponse.onSuccess(CommunityPostConverter.toPostBookmarkIdDTO(newPostBookmark));
    }

    @DeleteMapping("/{postId}/bookmarks")
    @Operation(summary = "커뮤니티 게시글 북마크 취소 API", description = "커뮤니티 게시글을 북마크 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelBookmarkCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
            @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        communityPostCommandService.cancelbookmarkCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 API", description = "커뮤니티 게시글을 좋아요하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse<CommunityPostResponseDTO.PostLikesIdDTO> likeCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        PostLikes newPostLikes = communityPostCommandService.likeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(CommunityPostConverter.toPostLikesIdDTO(newPostLikes));
    }

    @DeleteMapping("/{postId}/likes")
    @Operation(summary = "커뮤니티 게시글 좋아요 취소 API", description = "커뮤니티 게시글을 좋아요 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id"),
    })
    public BaseResponse cancelLikeCommunityPost(@RequestHeader("Authorization") String authorizationHeader,
            @Min(value = 1, message = "postId는 1 이상 이어야 합니다.") @PathVariable Long postId) {
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        communityPostCommandService.cancelLikeCommunityPost(userId, postId);
        return BaseResponse.onSuccess(null);
    }
}
