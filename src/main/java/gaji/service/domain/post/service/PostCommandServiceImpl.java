package gaji.service.domain.post.service;

import gaji.service.domain.common.converter.CategoryConverter;
import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.common.service.HashtagService;
import gaji.service.domain.enums.CommentStatus;
import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.repository.PostBookmarkRepository;
import gaji.service.domain.post.repository.PostLikesRepository;
import gaji.service.domain.post.repository.PostJpaRepository;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final PostJpaRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final PostLikesRepository postLikesRepository;
    private final UserQueryService userQueryService;
    private final PostQueryService postQueryService;
    private final CommentService commentService;
    private final HashtagService hashtagService;
    private final CategoryService categoryService;


    @Override
    public Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request) {
        User findUser = userQueryService.findUserById(userId);
        Post post = PostConverter.toPost(request, findUser);
        Post newPost = postRepository.save(post);

        // 해시태그 저장
        // TODO: 해시태그 벌크성 insert 적용
        if (request.getHashtagList() != null) {
            List<String> hashtagStringList = request.getHashtagList();
            List<Hashtag> hashtagEntityList = hashtagService.createHashtagEntityList(hashtagStringList);

            List<SelectHashtag> selectHashtagList = HashtagConverter.toSelectHashtagList(hashtagEntityList, post.getId(), request.getType());
            hashtagService.saveAllSelectHashtag(selectHashtagList);
        }

        // SelectCategory 저장
        if (request.getCategoryId() != null) {
            Long categoryId = request.getCategoryId();
            Category findCateogry = categoryService.findByCategoryId(categoryId);

            SelectCategory selectCategory = CategoryConverter.toSelectCategory(findCateogry, newPost.getId(), newPost.getType());
            categoryService.saveSelectCategory(selectCategory);
        }

        return newPost;
    }

    @Override
    public Comment writeCommentOnCommunityPost(Long userId, Long postId, Long parentCommentId, PostRequestDTO.WriteCommentDTO request) {
        User findUser = userQueryService.findUserById(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        // 저장
        Comment newComment = commentService.saveNewComment(
                commentService.createCommentByCheckParentCommentIdIsNull(parentCommentId, request, findUser, findPost)
        );

        // 게시글의 댓글 수 증가
        newComment.getPost().increaseCommentCnt();
        return newComment;
    }

    @Override
    public void hardDeleteComment(Long userId, Long commentId) {
        Comment findComment = commentService.findByCommentId(commentId);

        // 검증
        commentService.validCommentOwner(userId, findComment);

        // 삭제
        commentService.hardDeleteComment(findComment);

        // 게시글의 댓글 수 감소
        findComment.getPost().decreaseCommentCnt(); // TODO: 지연 로딩으로 쿼리 1개 더 날라감;
    }

    // TODO: 게시글 파일도 함께 삭제
    @Override
    public void hardDeleteCommunityPost(Long userId, Long postId) {
        Post findPost = postQueryService.findPostByPostId(postId);

        // 검증
        postQueryService.validPostOwner(userId, findPost);

        // 삭제
        hashtagService.deleteAllByEntityIdAndType(findPost.getId(), findPost.getType());
        postRepository.delete(findPost);
    }

    @Override
    public PostBookmark bookmarkCommunityPost(Long userId, Long postId) {
        User findUser = userQueryService.findUserById(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        // 검증
        postQueryService.validExistsPostBookmark(userId, findPost);

        // 저장
        PostBookmark newPostBookmark = postBookmarkRepository.save(PostConverter.toPostBookmark(findUser, findPost));

        // 게시글 북마크 수 증가
        newPostBookmark.getPost().increaseBookmarkCnt();
        return newPostBookmark;
    }

    @Override
    public void cancelbookmarkCommunityPost(Long userId, Long postId) {
        User findUser = userQueryService.findUserById(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        // 검증
        postQueryService.validPostOwner(findUser.getId(), findPost);

        // 삭제
        postBookmarkRepository.deleteByUserAndPost(findUser, findPost);

        // 게시글 북마크 수 감소
        findPost.decreaseBookmarkCnt();
    }

    @Override
    public PostLikes likeCommunityPost(Long userId, Long postId) {
        User findUser = userQueryService.findUserById(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        // 검증
        postQueryService.validExistsPostLikes(userId, findPost);

        // 저장
        PostLikes newPostLikes = postLikesRepository.save(PostConverter.toPostLikes(findUser, findPost));

        // 좋아요 수, 인기점수 증가
        findPost.increaseLikeCnt();
        findPost.increasePopularityScoreByLike();
        return newPostLikes;
    }

    @Override
    public void cancelLikeCommunityPost(Long userId, Long postId) {
        User findUser = userQueryService.findUserById(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        // 검증
        postQueryService.validPostOwner(findUser.getId(), findPost);

        // 삭제
        postLikesRepository.deleteByUserAndPost(findUser, findPost);

        // 좋아요 수, 인기점수 감소
        findPost.decreaseLikeCnt();
        findPost.decreasePopularityScoreByLike();
    }
}
