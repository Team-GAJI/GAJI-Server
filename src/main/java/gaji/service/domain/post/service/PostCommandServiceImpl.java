package gaji.service.domain.post.service;

import gaji.service.domain.common.converter.CategoryConverter;
import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Category;
import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectCategory;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.repository.SelectHashtagRepository;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.domain.common.service.HashtagService;
import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.enums.CommentStatus;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.repository.PostBookmarkRepository;
import gaji.service.domain.post.repository.PostLikesRepository;
import gaji.service.domain.post.repository.PostJpaRepository;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.code.UserErrorStatus;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final UserRepository userRepository;
    private final PostJpaRepository postRepository;
    private final PostQueryService postQueryService;
    private final CommentService commentService;
    private final HashtagService hashtagService;
    private final CategoryService categoryService;
    private final PostBookmarkRepository postBookmarkRepository;
    private final PostLikesRepository postLikesRepository;

    @Override
    public Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request) {
        User findUser = findUserByUserId(userId);
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

        // 카테고리 저장
        // TODO: 카테고리 벌크성 insert 적용
        if (request.getCategoryList() != null) {
            List<CategoryEnum> categoryEnumList = request.getCategoryList();
            List<Category> categoryEntityList = categoryService.createCategoryEntityList(categoryEnumList);

            List<SelectCategory> selectCategoryList = CategoryConverter.toSelectCategoryList(categoryEntityList, post.getId(), request.getType());
            categoryService.saveAllSelectCategory(selectCategoryList);
        }

        return newPost;
    }

    @Override
    public Comment writeCommentOnCommunityPost(Long userId, Long postId, Long parentCommentId, PostRequestDTO.WriteCommentDTO request) {
        User findUser = findUserByUserId(userId);
        Post findPost = postQueryService.findPostByPostId(postId);

        Comment newComment = createCommentByCheckParentCommentIdIsNull(parentCommentId, request, findUser, findPost);
        findPost.increaseCommentCnt();
        return commentService.saveNewComment(newComment);
    }

    @Override
    public void softDeleteComment(Long commentId) {
        Comment findComment = commentService.findByCommentId(commentId);
        findComment.updateStatus(CommentStatus.DELETE);
        findComment.getPost().decreaseCommentCnt(); // TODO: 지연 로딩으로 쿼리 1개 더 날라감;
    }

    // TODO: 게시글 파일도 함께 삭제
    @Override
    public void hardDeleteCommunityPost(Long postId) {
        Post findPost = postQueryService.findPostByPostId(postId);
        hashtagService.deleteAllByEntityIdAndType(findPost.getId(), findPost.getType());
        postRepository.delete(findPost);
    }

    @Override
    public PostBookmark bookmarkCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = postQueryService.findPostByPostId(postId);
        // TODO: 이미 북마크한 post인지 검증
        PostBookmark newPostBookmark = postBookmarkRepository.save(PostConverter.toPostBookmark(findUser, findPost));
        findPost.increaseBookmarkCnt();

        return newPostBookmark;
    }

    @Override
    public void cancelbookmarkCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = postQueryService.findPostByPostId(postId);
        postBookmarkRepository.deleteByUserAndPost(findUser, findPost);
        findPost.decreaseBookmarkCnt();
    }

    @Override
    public PostLikes likeCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = postQueryService.findPostByPostId(postId);
        // TODO: 이미 좋아요한 post인지 검증
        PostLikes newPostLikes = postLikesRepository.save(PostConverter.toPostLikes(findUser, findPost));
        findPost.increaseLikeCnt();
        findPost.increasePopularityScoreByLike();

        return newPostLikes;
    }

    @Override
    public void cancelLikeCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = postQueryService.findPostByPostId(postId);
        postLikesRepository.deleteByUserAndPost(findUser, findPost);
        findPost.decreaseLikeCnt();
        findPost.decreasePopularityScoreByLike();
    }

    private Comment createCommentByCheckParentCommentIdIsNull(Long parentCommentId, PostRequestDTO.WriteCommentDTO request, User findUser, Post findPost) {
        if (parentCommentId != null) {
            Comment parentComment = commentService.findByCommentId(parentCommentId);
            return PostConverter.toComment(request, findUser, findPost, parentComment);
        } else {
            return PostConverter.toComment(request, findUser, findPost, null);
        }
    }

    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus._USER_NOT_FOUND));
    }

}
