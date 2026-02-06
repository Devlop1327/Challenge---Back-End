package com.challenge.challege.service.impl;

import com.challenge.challege.domain.Post;
import com.challenge.challege.domain.Topic;
import com.challenge.challege.domain.User;
import com.challenge.challege.dto.CreatePostDTO;
import com.challenge.challege.dto.PostDTO;
import com.challenge.challege.dto.UserDTO;
import com.challenge.challege.repository.PostRepository;
import com.challenge.challege.repository.TopicRepository;
import com.challenge.challege.repository.UserRepository;
import com.challenge.challege.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PostDTO addPost(Long topicId, CreatePostDTO dto, String username) {
        User author = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        Post post = Post.builder()
                .content(dto.getContent())
                .author(author)
                .topic(topic)
                .build();
        Post saved = postRepository.save(post);
        return toDTO(saved);
    }

    @Override
    public Page<PostDTO> listPostsByTopic(Long topicId, Pageable pageable) {
        return postRepository.findByTopicId(topicId, pageable).map(this::toDTO);
    }

    private PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        User author = post.getAuthor();
        UserDTO u = new UserDTO();
        if (author != null) {
            u.setId(author.getId());
            u.setUsername(author.getUsername());
            u.setEmail(author.getEmail());
        }
        dto.setAuthor(u);
        dto.setTopicId(post.getTopic() != null ? post.getTopic().getId() : null);
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }
}
