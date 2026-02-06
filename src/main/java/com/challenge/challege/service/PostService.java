package com.challenge.challege.service;

import com.challenge.challege.dto.CreatePostDTO;
import com.challenge.challege.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDTO addPost(Long topicId, CreatePostDTO dto, String username);
    Page<PostDTO> listPostsByTopic(Long topicId, Pageable pageable);
}
