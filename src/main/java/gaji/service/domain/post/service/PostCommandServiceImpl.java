package gaji.service.domain.post.service;

import gaji.service.domain.common.converter.HashtagConverter;
import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.repository.HashtagRepository;
import gaji.service.domain.common.repository.SelectHashtagRepository;
import gaji.service.domain.enums.CommentStatus;
import gaji.service.domain.post.code.PostErrorStatus;
import gaji.service.domain.post.converter.PostConverter;
import gaji.service.domain.post.entity.Comment;
import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.entity.PostBookmark;
import gaji.service.domain.post.entity.PostLikes;
import gaji.service.domain.post.repository.CommentRepository;
import gaji.service.domain.post.repository.PostBookmarkRepository;
import gaji.service.domain.post.repository.PostLikesRepository;
import gaji.service.domain.post.repository.PostRepository;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final SelectHashtagRepository selectHashtagRepository;
    private final CommentRepository commentRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final PostLikesRepository postLikesRepository;

    @Override
    public Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request) {
        User findUser = findUserByUserId(userId);
        Post post = PostConverter.toPost(request, findUser);
        Post newPost = postRepository.save(post);

        if (request.getHashtagList() != null) {
            List<String> hashtagStringList = request.getHashtagList();
            List<Hashtag> hashtagEntityList = convertHashtagStringListToHashtagList(hashtagStringList);

            List<SelectHashtag> selectHashtagList = HashtagConverter.toSelectHashtagList(hashtagEntityList, post.getId(), request.getType());
            selectHashtagRepository.saveAll(selectHashtagList);
        }
        return newPost;
    }


    @Override
    public Comment writeCommentOnCommunityPost(Long userId, Long postId, Long parentCommentId, PostRequestDTO.WriteCommentDTO request) {
        User findUser = findUserByUserId(userId);
        Post findPost = findPostByPostId(postId);

        Comment newComment = createCommentByCheckParentCommentIdIsNull(parentCommentId, request, findUser, findPost);
        return commentRepository.save(newComment);
    }

    @Override
    public void softDeleteComment(Long commentId) {
        Comment findComment = findCommentByCommentId(commentId);
        findComment.updateStatus(CommentStatus.DELETE);
    }

    // TODO: 게시글 파일도 함께 삭제
    @Override
    public void hardDeleteCommunityPost(Long postId) {
        Post findPost = findPostByPostId(postId);
        selectHashtagRepository.deleteAllByEntityIdAndType(findPost.getId(), findPost.getType());
        postRepository.delete(findPost);
    }

    @Override
    public PostBookmark bookmarkCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = findPostByPostId(postId);
        // TODO: 이미 북마크한 post인지 검증
        PostBookmark newPostBookmark = postBookmarkRepository.save(PostConverter.toPostBookmark(findUser, findPost));
        findPost.increaseBookmarkCnt();

        return newPostBookmark;
    }

    @Override
    public void cancelbookmarkCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = findPostByPostId(postId);
        postBookmarkRepository.deleteByUserAndPost(findUser, findPost);
        findPost.decreaseBookmarkCnt();
    }

    @Override
    public PostLikes likeCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = findPostByPostId(postId);
        // TODO: 이미 좋아요한 post인지 검증
        PostLikes newPostLikes = postLikesRepository.save(PostConverter.toPostLikes(findUser, findPost));
        findPost.increaseLikeCnt();

        return newPostLikes;
    }

    @Override
    public void cancelLikeCommunityPost(Long userId, Long postId) {
        User findUser = findUserByUserId(userId);
        Post findPost = findPostByPostId(postId);
        postLikesRepository.deleteByUserAndPost(findUser, findPost);
        findPost.decreaseLikeCnt();
    }

    private Comment createCommentByCheckParentCommentIdIsNull(Long parentCommentId, PostRequestDTO.WriteCommentDTO request, User findUser, Post findPost) {
        if (parentCommentId != null) {
            Comment parentComment = findCommentByCommentId(parentCommentId);
            return PostConverter.toComment(request, findUser, findPost, parentComment);
        } else {
            return PostConverter.toComment(request, findUser, findPost, null);
        }
    }

    // 이미 존재하는 해시태그는 조회, 존재하지 않는 해시태그는 생성해서 List로 반환하는 메서드
    private List<Hashtag> convertHashtagStringListToHashtagList(List<String> hashtagStringList) {
        return hashtagStringList.stream()
                .map(hashtag -> {
                    if (hashtagRepository.existsByName(hashtag)) {
                        return hashtagRepository.findByName(hashtag);
                    } else {
                        return hashtagRepository.save(HashtagConverter.toHashtag(hashtag));
                    }
                })
                .collect(Collectors.toList());
    }

    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._USER_NOT_FOUND));
    }

    private Post findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._POST_NOT_FOUND));
    }

    private Comment findCommentByCommentId(Long parentCommentId) {
        return commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RestApiException(PostErrorStatus._COMMENT_NOT_FOUND));
    }
}
