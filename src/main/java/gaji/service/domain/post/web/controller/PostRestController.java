package gaji.service.domain.post.web.controller;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.service.PostCommandService;
import gaji.service.domain.post.web.dto.PostRequestDTO;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community-posts")
public class PostRestController {

    private final PostCommandService postCommandService;

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 업로드 API",description = "커뮤니티의 게시글을 업로드하는 API입니다. 게시글 유형과 제목, 본문 내용을 검증합니다.")
    public BaseResponse<Long> uploadPost(Long userId, @RequestBody @Valid PostRequestDTO.UploadPostDTO request) {
        Post newPost = postCommandService.uploadPost(userId, request);
        return BaseResponse.onSuccess(newPost.getId());
    }


}
