package com.challenge.challege.service.impl;

import com.challenge.challege.domain.Topic;
import com.challenge.challege.domain.User;
import com.challenge.challege.dto.CreateTopicDTO;
import com.challenge.challege.dto.TopicDTO;
import com.challenge.challege.dto.UserDTO;
import com.challenge.challege.repository.TopicRepository;
import com.challenge.challege.repository.UserRepository;
import com.challenge.challege.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TopicDTO createTopic(CreateTopicDTO dto, String username) {
        User author = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Topic topic = Topic.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .author(author)
                .build();
        Topic saved = topicRepository.save(topic);
        return toDTO(saved);
    }

    @Override
    public Page<TopicDTO> listTopics(Pageable pageable) {
        return topicRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public TopicDTO getTopic(Long id) {
        Topic t = topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        return toDTO(t);
    }

    @Override
    public TopicDTO updateTopic(Long id, CreateTopicDTO dto, String username) {
        Topic existing = topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        if (existing.getAuthor() == null || !existing.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("Only the author can update the topic");
        }
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        Topic saved = topicRepository.save(existing);
        return toDTO(saved);
    }

    @Override
    public void deleteTopic(Long id, String username) {
        Topic existing = topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        if (existing.getAuthor() == null || !existing.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("Only the author can delete the topic");
        }
        topicRepository.delete(existing);
    }

    private TopicDTO toDTO(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setDescription(topic.getDescription());
        UserDTO u = new UserDTO();
        User author = topic.getAuthor();
        if (author != null) {
            u.setId(author.getId());
            u.setUsername(author.getUsername());
            u.setEmail(author.getEmail());
        }
        dto.setAuthor(u);
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setUpdatedAt(topic.getUpdatedAt());
        return dto;
    }
}
