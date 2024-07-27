package gaji.service.domain.post.service;

import gaji.service.domain.post.entity.Post;
import gaji.service.domain.post.web.dto.PostRequestDTO;

public interface PostCommandService {

    Post uploadPost(Long userId, PostRequestDTO.UploadPostDTO request);

}
