package com.challenge.challege.service;

import com.challenge.challege.dto.CreateTopicDTO;
import com.challenge.challege.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    TopicDTO createTopic(CreateTopicDTO dto, String username);
    Page<TopicDTO> listTopics(Pageable pageable);
    TopicDTO getTopic(Long id);
    TopicDTO updateTopic(Long id, CreateTopicDTO dto, String username);
    void deleteTopic(Long id, String username);
}
